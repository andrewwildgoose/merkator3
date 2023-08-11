package com.merkator3.merkator3api.models;

import io.jenetics.jpx.GPX;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Document(collection = "routes")
public class Route {
    @Id private ObjectId id;
    @Field("routeName") private String routeName;
    @Field("routeDescription") private String routeDescription;
    @Field("routeGPXString") private String routeGpxString;

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
        //System.out.println(this.routeGpxString);
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


}