package com.merkator3.merkator3api.models;

import com.merkator3.merkator3api.MapTools.MapBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

/**
 * A PlannedTrip is a collection of Routes which the user has not yet undertaken
 * which they want to analyse together.
 */
@Document(collection = "trips")
public class PlannedTrip implements Trip {

    @Id private ObjectId id;
    @Field("tripName") private String tripName;
    @Field("tripDescription") private String tripDescription;
    @Field("tripRoutes") private List<Route> tripRoutes;
    @Field("tripStaticMapURL") private String tripStaticMapUrl;

    public PlannedTrip(String tripName){
        this.tripName = tripName;
    };

    @Override
    public ObjectId getId() {
        return id;
    }

    @Override
    public String getTripName() {
        return tripName;
    }

    @Override
    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    @Override
    public String getTripDescription() {
        return tripDescription;
    }

    @Override
    public void setTripDescription(String tripDescription) {
        this.tripDescription = tripDescription;
    }

    @Override
    public List<Route> getTripRoutes() {
        return tripRoutes;
    }

    @Override
    public void setTripRoutes(List<Route> tripRoutes) {
        this.tripRoutes = tripRoutes;
    }

    public void addRoute(Route route){
        if (tripRoutes == null) {
            tripRoutes = new ArrayList<>();
        }
        tripRoutes.add(route);
    }

    @Override
    public void setTripStaticMapUrl(String mapBoxKey) {
        MapBuilder mapBuilder = new MapBuilder(mapBoxKey);
        this.tripStaticMapUrl = mapBuilder.generateStaticMapImageUrl(this.tripRoutes);
    }

    @Override
    public String getTripStaticMapUrl(String mapBoxKey) {
        if (this.tripStaticMapUrl == null) {
            this.setTripStaticMapUrl(mapBoxKey);
        }
        return this.tripStaticMapUrl;
    }
}
