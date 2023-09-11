package com.merkator3.merkator3api.MapTools;

import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.geojson.utils.PolylineUtils;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

import com.mapbox.api.staticmap.v1.MapboxStaticMap;
import com.mapbox.api.staticmap.v1.models.StaticPolylineAnnotation;

import static com.mapbox.geojson.utils.PolylineUtils.simplify;

import com.merkator3.merkator3api.models.route.RouteMarker;
import com.merkator3.merkator3api.models.route.completed.CompletedRoute;
import io.jenetics.jpx.GPX;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import java.util.List;

import static com.merkator3.merkator3api.GpxTools.GpxReader.extractCoordinatesFromGpx;

public class MapBuilder {


    private final String mapBoxKey; // Non-static field

    public MapBuilder(@Value("${merkator.api.mapBoxKey}") String mapBoxKey) {
        this.mapBoxKey = mapBoxKey;
    }

    @SneakyThrows
    public <T extends RouteMarker> String generateStaticMapImageUrl(List<T> routes) {

        Random random = new Random();
        List<StaticPolylineAnnotation> routePolyLines = new ArrayList<>();

        MapboxStaticMap.Builder mapBuilder = MapboxStaticMap.builder()
                .accessToken(mapBoxKey)
                .user("merkator1")
                .styleId("cllax8yio00sf01pdfsrn1gmo")
                .cameraAuto(true)
                .width(150)
                .height(150)
                .retina(false);

        for (T route : routes) {
            GPX gpx = route.getRouteGpx();
            List<Point> points = extractCoordinatesFromGpx(gpx).stream()
                    .map(coordinate -> Point.fromLngLat(coordinate.getLongitude(), coordinate.getLatitude()))
                    .collect(Collectors.toList());

            //Reduce the number of points to fit URL length constraints
            List<Point> simplifiedPoints = simplify(points, 0.00175);
            LineString lineString = LineString.fromLngLats(simplifiedPoints);

            if (route.getMapLineColor() == null) {
                // Generate a random color if the route does not yet have a colour
                int red = random.nextInt(255);
                int green = random.nextInt(255);
                int blue = random.nextInt(255);

                // Save the RGB values for consistent colouring of this route.
                route.setMapLineColor(red, green, blue);
            }

            StaticPolylineAnnotation polylineAnnotation = StaticPolylineAnnotation.builder()
                    .polyline(PolylineUtils.encode(simplifiedPoints, 5))
                    .strokeColor(
                            route.getMapLineColor().get(0),
                            route.getMapLineColor().get(1),
                            route.getMapLineColor().get(2)
                    )
                    .strokeOpacity(route instanceof CompletedRoute ? 0.9F : 0.6F)
                    .strokeWidth(6.0)
                    .build();
            routePolyLines.add(polylineAnnotation);
        }
        mapBuilder.staticPolylineAnnotations(routePolyLines);
        MapboxStaticMap staticMap = mapBuilder.build();
        return staticMap.url().toString();
    }
}

