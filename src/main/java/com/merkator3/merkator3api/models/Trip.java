package com.merkator3.merkator3api.models;

import com.merkator3.merkator3api.MapTools.MapBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "trips")
public class Trip {

    @Id private ObjectId id;
    @Field("tripName") private String tripName;
    @Field("tripDescription") private String tripDescription;
    @Field("tripRoutes") private List<Route> tripRoutes;
    @Field("tripStaticMapURL") private String tripStaticMapUrl;

    public Trip(String tripName){
        this.tripName = tripName;
    };

    public ObjectId getId() {
        return id;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getTripDescription() {
        return tripDescription;
    }

    public void setTripDescription(String tripDescription) {
        this.tripDescription = tripDescription;
    }

    public List<Route> getTripRoutes() {
        return tripRoutes;
    }

    public void setTripRoutes(List<Route> tripRoutes) {
        this.tripRoutes = tripRoutes;
    }

    public void addRoute(Route route){
        if (tripRoutes == null) {
            tripRoutes = new ArrayList<>();
        }
        tripRoutes.add(route);
    }

    public void setTripStaticMapUrl(String mapBoxKey) {
        MapBuilder mapBuilder = new MapBuilder(mapBoxKey);
        this.tripStaticMapUrl = mapBuilder.generateStaticMapImageUrl(this.tripRoutes);
    }

    public String getTripStaticMapUrl(String mapBoxKey) {
        if (this.tripStaticMapUrl == null) {
            this.setTripStaticMapUrl(mapBoxKey);
        }
        return this.tripStaticMapUrl;
    }
}
