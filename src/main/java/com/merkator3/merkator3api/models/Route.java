package com.merkator3.merkator3api.models;

import com.merkator3.merkator3api.GpxTools.GpxBuilder;
import io.jenetics.jpx.GPX;
import io.jenetics.jpx.Metadata;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document(collection = "routes")
public class Route {
    @Id private ObjectId id;
    @Field("routeName") private String routeName;
    @Field("routeDescription") private String routeDescription;
    @Field("routeGPX") private GPX routeGpx;

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

    public GPX getRouteGpx() {
        return routeGpx;
    }

    public void setRouteGpx(GPX routeGpx) {
        //retrieve metadata
        Metadata metadata = routeGpx.getMetadata().orElse(null);

        //database will not accept empty time metadata so check and update to current time if empty and repopulate the metadata.
        if (metadata != null && metadata.getTime().isEmpty()) {
            metadata = GpxBuilder.populateGPXMetadataTime(metadata);
        }


        this.routeGpx = GPX.builder()
                .version(GPX.Version.V11)
                .metadata(metadata)
                .wayPoints(routeGpx.getWayPoints())
                .routes(routeGpx.getRoutes())
                .tracks(routeGpx.getTracks())
                .build();
    }
}