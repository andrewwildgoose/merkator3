package com.merkator3.merkator3api.MapTools;

import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.geojson.utils.PolylineUtils;

import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;

import com.mapbox.api.staticmap.v1.MapboxStaticMap;
import com.mapbox.api.staticmap.v1.models.StaticPolylineAnnotation;
import com.mapbox.geojson.Feature;
import static com.mapbox.geojson.utils.PolylineUtils.simplify;

import com.merkator3.merkator3api.models.Route;
import io.jenetics.jpx.GPX;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import java.util.List;

import static com.merkator3.merkator3api.GpxTools.GpxReader.extractCoordinatesFromGPX;

public class MapBuilder {


    private final String mapBoxKey; // Non-static field

    public MapBuilder(@Value("${merkator.api.mapBoxKey}") String mapBoxKey) {
        this.mapBoxKey = mapBoxKey;
    }

    @SneakyThrows
    public String generateStaticMapImageUrl(List<Route> routes) {

        Random random = new Random();


        MapboxStaticMap.Builder mapBuilder = MapboxStaticMap.builder()
                .accessToken(mapBoxKey)
                .user("merkator1")
                .styleId("cllax8yio00sf01pdfsrn1gmo")
                .cameraAuto(true)
                .width(150)
                .height(150)
                .retina(false);

        for (Route route : routes) {
            GPX gpx = route.getRouteGpx();
            List<Point> points = extractCoordinatesFromGPX(gpx).stream()
                    .map(coordinate -> Point.fromLngLat(coordinate.getLongitude(), coordinate.getLatitude()))
                    .collect(Collectors.toList());

            //Reduce the number of points to fit URL length constraints
            List<Point> simplifiedPoints = simplify(points, 0.0175);
            LineString lineString = LineString.fromLngLats(simplifiedPoints);

            // Generate a random color
            int red = random.nextInt(255);
            int green = random.nextInt(255);
            int blue = random.nextInt(255);

            // Save the RGB values for consistent colouring of this route.
            route.setMapLineColor(red, green, blue);

            StaticPolylineAnnotation polylineAnnotation = StaticPolylineAnnotation.builder()
                    .polyline(PolylineUtils.encode(simplifiedPoints, 5))
                    .strokeColor(red,green,blue)
                    .strokeOpacity(0.9F)
                    .strokeWidth(6.0)
                    .build();

            mapBuilder.staticPolylineAnnotations(Collections.singletonList(polylineAnnotation));
        }

        MapboxStaticMap staticMap = mapBuilder.build();
        return staticMap.url().toString();
    }
}

