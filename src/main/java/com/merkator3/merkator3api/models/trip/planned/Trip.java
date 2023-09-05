package com.merkator3.merkator3api.models.trip.planned;

import com.merkator3.merkator3api.MapTools.MapBuilder;
import com.merkator3.merkator3api.models.route.planned.Route;
import com.merkator3.merkator3api.models.trip.TripMarker;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A PlannedTrip is a collection of Routes which the user has not yet undertaken
 * which they want to analyse together.
 */
@Document(collection = "trips")
public class Trip implements TripMarker {

    @Id private ObjectId id;
    @Field("tripName") private String tripName;
    @Field("tripDescription") private String tripDescription;
    @Field("tripRoutes") private List<ObjectId> tripRoutes;
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

    public List<ObjectId> getTripRoutes() {
        if (this.tripRoutes == null) {
            this.tripRoutes = new ArrayList<>();
        }
        return tripRoutes;
    }

    public void setTripRoutes(List<Route> tripRoutes) {
        this.tripRoutes = tripRoutes.stream()
                .map(Route::getId)
                .collect(Collectors.toList());
    }

    public void addRoute(Route route){
        if (tripRoutes == null) {
            tripRoutes = new ArrayList<>();
        }
        tripRoutes.add(route.getId());
    }

    public void setTripStaticMapUrl(String mapUrl) {
        this.tripStaticMapUrl = mapUrl;
    }

    public String getTripStaticMapUrl() {
        return this.tripStaticMapUrl;
    }
}
