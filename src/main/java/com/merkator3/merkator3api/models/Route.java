package com.merkator3.merkator3api.models;

import com.merkator3.merkator3api.GpxTools.GpxBuilder;
import io.jenetics.jpx.GPX;
import io.jenetics.jpx.Metadata;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Path;


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
        return GPX.read((Path) new ByteArrayInputStream(routeGpxString.getBytes()));
    }

    public void setRouteGpx(GPX routeGpx) {
        this.routeGpxString = routeGpx.toString();

    }
}