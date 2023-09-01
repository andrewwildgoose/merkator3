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

    public List<String> getTripRouteNames() {
        return this.getTripRoutes().stream()
                .map(Route::getRouteName)
                .collect(Collectors.toList());
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
