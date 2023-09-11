package com.merkator3.merkator3api.models.trip.completed;

import com.merkator3.merkator3api.MapTools.MapBuilder;
import com.merkator3.merkator3api.models.route.RouteMarker;
import com.merkator3.merkator3api.models.route.completed.CompletedRoute;
import com.merkator3.merkator3api.models.route.planned.Route;
import com.merkator3.merkator3api.models.trip.TripMarker;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A CompletedTrip is a collection of Routes of which the user has undertaken at least one
 * which they want to analyse together.
 */
@Document(collection = "completedTrips")
public class CompletedTrip implements TripMarker {

    @Id
    private ObjectId id;
    @Field("tripName") private String tripName;
    @Field("hasParentTrip") private final Boolean hasParentTrip;
    @Field("parentTripName") private String parentTripName;
    @Field("parentTripId") private ObjectId parentTripId;
    @Field("tripDescription") private String tripDescription;
    @Field("tripRoutes") private List<ObjectId> tripRoutes;
    @Field("tripCompletedRoutes") private List<ObjectId> tripCompletedRoutes;
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

    public boolean hasParentTrip() {
        return hasParentTrip;
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

    public List<ObjectId> getTripRoutes() {
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

    public List<ObjectId> getCompletedRoutes() {
        return tripCompletedRoutes;
    }

    public void setCompletedRoutes(List<CompletedRoute> completedRoutes) {
        for (CompletedRoute completedRoute : completedRoutes) {
            addCompletedRoute(completedRoute.getId());
        }
    }

    public void addCompletedRoute(ObjectId completedRoute) {
        if (tripCompletedRoutes == null) {
            tripCompletedRoutes = new ArrayList<>();
        }
        tripCompletedRoutes.add(completedRoute);
    }

    public void addCompletedRoute(int index, ObjectId completedRoute) {
        tripCompletedRoutes.set(index, completedRoute);
    }

    //TODO: map functionality for a mixture of non & completed routes needs consideration.
    public void setTripStaticMapUrl(String mapUrl) {
        this.tripStaticMapUrl = mapUrl;
    }

    public String getTripStaticMapUrl() {
        return this.tripStaticMapUrl;
    }
}
