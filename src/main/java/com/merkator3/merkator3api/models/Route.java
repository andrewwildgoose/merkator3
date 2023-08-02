package com.merkator3.merkator3api.models;

import com.merkator3.merkator3api.GpxTools.GpxBuilder;
import io.jenetics.jpx.GPX;
import io.jenetics.jpx.Length;
import io.jenetics.jpx.Metadata;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;


@Document(collection = "routes")
public class Route {
    @Id private ObjectId id;
    @Field("routeName") private String routeName;
    @Field("routeDescription") private String routeDescription;
    @Field("routeGPX") private String routeGpxString;

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

        // Convert the custom Length object in GPX to double values
        gpx.getTracks().forEach(track -> {
            track.getSegments().forEach(segment -> {
                segment.getPoints().forEach(point -> {
                    Optional<Length> elevationOptional = point.getElevation();
                    double elevation = elevationOptional.map(Length::doubleValue).orElse(0.0);
                    point = point.toBuilder().ele(elevation).build();
                });
            });
        });

        // Delete the temporary file
        Files.delete(tempFile);

        return gpx;
    }

    public void setRouteGpx(GPX routeGpx) throws IOException {
        Path tempFile = Files.createTempFile("gpx", ".xml");
        GPX.write(routeGpx, tempFile);

        byte[] bytes = Files.readAllBytes(tempFile);
        String xmlString = new String(bytes);

        // Delete the temporary file
        Files.delete(tempFile);

        this.routeGpxString = xmlString;

    }


}