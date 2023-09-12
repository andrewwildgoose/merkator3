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
    @Field("tripLength") private Double tripLength;
    @Field("tripElevationGain") private Double tripElevationGain;
    @Field("tripElevationLoss") private Double tripElevationLoss;
    @Field("tripCompletedLength") private Double tripCompletedLength;
    @Field("tripCompletedElevationGain") private Double tripCompletedElevationGain;
    @Field("tripCompletedElevationLoss") private Double tripCompletedElevationLoss;
    @Field("tripElapsedTime") private Double tripElapsedTime;
    @Field("tripMovingTime") private Double tripMovingTime;
    @Field("tripAvgSpeedElapsed") private Double tripAvgSpeedElapsed;
    @Field("tripAvgSpeedMoving") private Double tripAvgSpeedMoving;
    @Field("tripRouteNames") private List<String> tripRouteNames;
    @Field("tripCompletedRouteNames") private List<String> tripCompletedRouteNames;
    @Field("tripRouteColours") private List<List<Integer>> tripRouteColours;
    @Field("tripCompletedRouteColours") private List<List<Integer>> tripCompletedRouteColours;
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

    public void setTripStaticMapUrl(String mapUrl) {
        this.tripStaticMapUrl = mapUrl;
    }

    public String getTripStaticMapUrl() {
        return this.tripStaticMapUrl;
    }
    public Double getTripLength() {
        return tripLength;
    }

    public void setTripLength(Double tripLength) {
        this.tripLength = tripLength;
    }

    public Double getTripElevationGain() {
        return tripElevationGain;
    }

    public void setTripElevationGain(Double tripElevationGain) {
        this.tripElevationGain = tripElevationGain;
    }

    public Double getTripElevationLoss() {
        return tripElevationLoss;
    }

    public void setTripElevationLoss(Double tripElevationLoss) {
        this.tripElevationLoss = tripElevationLoss;
    }

    public Double getTripCompletedLength() {
        return tripCompletedLength;
    }

    public void setTripCompletedLength(Double tripCompletedLength) {
        this.tripCompletedLength = tripCompletedLength;
    }

    public Double getTripCompletedElevationGain() {
        return tripCompletedElevationGain;
    }

    public void setTripCompletedElevationGain(Double tripCompletedElevationGain) {
        this.tripCompletedElevationGain = tripCompletedElevationGain;
    }

    public Double getTripCompletedElevationLoss() {
        return tripCompletedElevationLoss;
    }

    public void setTripCompletedElevationLoss(Double tripCompletedElevationLoss) {
        this.tripCompletedElevationLoss = tripCompletedElevationLoss;
    }

    public Double getTripElapsedTime() {
        return tripElapsedTime;
    }

    public void setTripElapsedTime(Double tripElapsedTime) {
        this.tripElapsedTime = tripElapsedTime;
    }

    public Double getTripMovingTime() {
        return tripMovingTime;
    }

    public void setTripMovingTime(Double tripMovingTime) {
        this.tripMovingTime = tripMovingTime;
    }

    public Double getTripAvgSpeedElapsed() {
        return tripAvgSpeedElapsed;
    }

    public void setTripAvgSpeedElapsed(Double tripAvgSpeedElapsed) {
        this.tripAvgSpeedElapsed = tripAvgSpeedElapsed;
    }

    public Double getTripAvgSpeedMoving() {
        return tripAvgSpeedMoving;
    }

    public void setTripAvgSpeedMoving(Double tripAvgSpeedMoving) {
        this.tripAvgSpeedMoving = tripAvgSpeedMoving;
    }

    public List<String> getTripRouteNames() {
        return tripRouteNames;
    }

    public void setTripRouteNames(List<String> tripRouteNames) {
        this.tripRouteNames = tripRouteNames;
    }

    public List<String> getTripCompletedRouteNames() {
        return tripCompletedRouteNames;
    }

    public void setTripCompletedRouteNames(List<String> tripCompletedRouteNames) {
        this.tripCompletedRouteNames = tripCompletedRouteNames;
    }

    public List<List<Integer>> getTripRouteColours() {
        return tripRouteColours;
    }

    public void setTripRouteColours(List<List<Integer>> tripRouteColours) {
        this.tripRouteColours = tripRouteColours;
    }

    public List<List<Integer>> getTripCompletedRouteColours() {
        return tripCompletedRouteColours;
    }

    public void setTripCompletedRouteColours(List<List<Integer>> tripCompletedRouteColours) {
        this.tripCompletedRouteColours = tripCompletedRouteColours;
    }
}
