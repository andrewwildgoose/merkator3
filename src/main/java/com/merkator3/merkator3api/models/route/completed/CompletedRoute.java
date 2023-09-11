package com.merkator3.merkator3api.models.route.completed;

import com.merkator3.merkator3api.MapTools.MapBuilder;
import com.merkator3.merkator3api.models.route.RouteMarker;
import io.jenetics.jpx.GPX;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Document(collection = "completedRoutes")

public class CompletedRoute implements RouteMarker {

    @Id
    private ObjectId id;
    @Field("routeName") private String routeName;


    @Field("hasParentRoute") private Boolean hasParentRoute;
    @Field("parentRouteName") private String parentRouteName;
    @Field("parentRouteId") private ObjectId parentRouteId;
    @Field("routeDescription") private String routeDescription;
    @Field("routeGPXString") private String routeGpxString;
    @Field("mapLineColor") private List<Integer> mapLineColor;
    @Field("routeStaticMapURL") private String routeStaticMapUrl;

    public CompletedRoute(String routeName, Boolean hasParentRoute) {
        this.routeName = routeName;
        this.hasParentRoute = hasParentRoute;
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

    public Boolean getHasParentRoute() {
        return hasParentRoute;
    }

    public void setParentRouteName(String parentRouteName) {
        this.parentRouteName = parentRouteName;
    }

    public String getParentRouteName() {
        return parentRouteName;
    }

    public void setParentRouteId(ObjectId parentRouteId) {
        this.parentRouteId = parentRouteId;
    }

    public ObjectId getParentRouteId() {
        return parentRouteId;
    }

    public String getRouteDescription() {
        return routeDescription;
    }

    public void setRouteDescription(String routeDescription) {
        this.routeDescription = routeDescription;
    }

    public GPX getRouteGpx() throws IOException {
        Path tempFile = Files.createTempFile("gpx", ".gpx");
        Files.writeString(tempFile, routeGpxString);

        String fileContent = Files.readString(tempFile);

        GPX gpx = GPX.read(tempFile);

        // Delete the temporary file
        Files.delete(tempFile);

        return gpx;
    }


    // Set's the simple version of the GPX information as a String for use, if required, by the front end.
    public void setRouteGpxString(Path path) {
        try {
            this.routeGpxString = Files.lines(path)
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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


    public void setRouteStaticMapUrl(String mapUrl) {
        this.routeStaticMapUrl = mapUrl;
    }

    public String getRouteStaticMapURL() {
        return this.routeStaticMapUrl;
    }

    public List<Integer> getMapLineColor() {
        return mapLineColor;
    }

    // Store the RGB values for the map line colour of this map.
    public void setMapLineColor(int red, int green, int blue) {
        this.mapLineColor = List.of(red, green, blue);
    }

}
