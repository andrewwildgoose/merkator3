package com.merkator3.merkator3api.models;

import com.mapbox.api.staticmap.v1.MapboxStaticMap;
import com.merkator3.merkator3api.MapTools.MapBuilder;
import io.jenetics.jpx.GPX;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Document(collection = "routes")
public class Route {
    @Id private ObjectId id;
    @Field("routeName") private String routeName;
    @Field("routeDescription") private String routeDescription;
    @Field("routeGPXString") private String routeGpxString;
    @Field("mapLineColor") private List<Integer> mapLineColor;
    @Field("routeStaticMapURL") private String routeStaticMapURL;


    public Route(String routeName) {
        this.routeName = routeName;
    }

    public ObjectId getId() {
        return id;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getRouteDescription() {
        return routeDescription;
    }

    public void setRouteDescription(String routeDescription) {
        this.routeDescription = routeDescription;
    }

    public GPX getRouteGpx() throws IOException {
        Path tempFile = Files.createTempFile(".gpx", ".xml");
        Files.writeString(tempFile, routeGpxString);

        GPX gpx = GPX.read(tempFile);

        // Delete the temporary file
        Files.delete(tempFile);

        return gpx;
    }

    // Set's the simple version of the GPX information as a String for use, if required, by the front end.
    public void setRouteGpxString(GPX routeGpx) throws IOException {
        Path tempFile = Files.createTempFile("gpx", ".xml");
        GPX.write(routeGpx, tempFile);

        byte[] bytes = Files.readAllBytes(tempFile);
        String xmlString = new String(bytes);

        // Delete the temporary file
        Files.delete(tempFile);

        this.routeGpxString = xmlString;

    }

    public String getRouteGpxString() throws IOException {
        return this.routeGpxString;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", routeName='" + routeName + '\'' +
                ", routeDescription='" + routeDescription + '\'' +
                ", routeGpxString='" + routeGpxString + '\'' +
                '}';
    }


    public void setRouteStaticMapURL(String mapBoxKey) {
        List<Route> singleRouteList = List.of(this);
        MapBuilder mapBuilder = new MapBuilder(mapBoxKey);
        this.routeStaticMapURL = mapBuilder.generateStaticMapImageUrl(singleRouteList);
    }

    public String getRouteStaticMapURL() {
        return this.routeStaticMapURL;
    }

    public List<Integer> getMapLineColor() {
        return mapLineColor;
    }

    // Store the RGB values for the map line colour of this map.
    public void setMapLineColor(int red, int green, int blue) {
        this.mapLineColor = List.of(red, green, blue);
    }
}