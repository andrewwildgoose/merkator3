package com.merkator3.merkator3api.models;

import com.merkator3.merkator3api.MapTools.MapBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A CompletedTrip is a collection of Routes of which the user has undertaken at least one
 * which they want to analyse together.
 */
@Document(collection = "completedTrips")
public class CompletedTrip implements TripMarker{

    @Id
    private ObjectId id;
    @Field("tripName") private String tripName;
    @Field("hasParentTrip") private Boolean hasParentTrip;
    @Field("parentTripName") private String parentTripName;
    @Field("parentTripId") private ObjectId parentTripId;
    @Field("tripDescription") private String tripDescription;
    @Field("tripRoutes") private List<Route> tripRoutes;
    @Field("tripCompletedRoutes") private List<CompletedRoute> tripCompletedRoutes;
    @Field("tripStaticMapURL") private String tripStaticMapUrl;

    public CompletedTrip(String tripName, Boolean hasParentTrip){
        this.tripName = tripName;
        this.hasParentTrip = hasParentTrip;
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

    public void setParentTripName(String parentTripName) {
        this.parentTripName = parentTripName;
    }

    public String getParentTripName() {
        return parentTripName;
    }

    public void setParentTripId(ObjectId parentTripId) {
        this.parentTripId = parentTripId;
    }

    public ObjectId getParentTripId() {
        return parentTripId;
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

    public List<CompletedRoute> getCompletedRoutes() {
        return tripCompletedRoutes;
    }

    public void setCompletedRoutes(List<CompletedRoute> completedRoutes) {
        for (CompletedRoute completedRoute : completedRoutes) {
            addCompletedRoute(completedRoute);

        }
    }

    public void addCompletedRoute(CompletedRoute completedRoute) {
        if (tripCompletedRoutes == null) {
            tripCompletedRoutes = new ArrayList<>();
        }

        ObjectId parentRouteId = completedRoute.getParentRouteId();

        // Check if a completed route with the same parentRouteId already exists
        Optional<CompletedRoute> existingCompletedRouteOptional = tripCompletedRoutes.stream()
                .filter(route -> route.getParentRouteId().equals(parentRouteId))
                .findFirst();

        if (existingCompletedRouteOptional.isPresent()) {
            // Replace the existing completed route with the new one
            CompletedRoute existingCompletedRoute = existingCompletedRouteOptional.get();
            int existingRouteIndex = tripCompletedRoutes.indexOf(existingCompletedRoute);
            tripCompletedRoutes.set(existingRouteIndex, completedRoute);
        } else {
            // No existing completed route with the same parentRouteId, add the new one
            tripCompletedRoutes.add(completedRoute);
        }
    }

    //TODO: map functionality for a mixture of non & completed routes needs consideration.
//    public void setTripStaticMapUrl(String mapBoxKey) {
//        MapBuilder mapBuilder = new MapBuilder(mapBoxKey);
//        List<RouteMarker> allRoutes = new ArrayList<>();
//        allRoutes.addAll(this.tripRoutes);
//        allRoutes.addAll(this.tripCompletedRoutes);
//        this.tripStaticMapUrl = mapBuilder.generateStaticMapImageUrl(allRoutes);
//    }
//
//    public String getTripStaticMapUrl(String mapBoxKey) {
//        if (this.tripStaticMapUrl == null) {
//            this.setTripStaticMapUrl(mapBoxKey);
//        }
//        return this.tripStaticMapUrl;
//    }
}
