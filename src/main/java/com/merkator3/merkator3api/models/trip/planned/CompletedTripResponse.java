package com.merkator3.merkator3api.models.trip.planned;

import com.merkator3.merkator3api.models.route.planned.RouteMapping;
import org.bson.types.ObjectId;

import java.util.List;

public class CompletedTripResponse {
    private ObjectId id;
    private String idString;
    private String tripName;
    private String tripDescription;
    private Double tripLength;
    private Double tripElevationGain;
    private Double tripElevationLoss;
    private Double tripCompletedLength;
    private Double tripCompletedElevationGain;
    private Double tripCompletedElevationLoss;
    private Double tripElapsedTime;
    private Double tripMovingTime;
    private Double tripAvgSpeedElapsed;
    private Double tripAvgSpeedMoving;
    private List<String> tripRouteNames;
    private List<String> tripCompletedRouteNames;
    private List<RouteMapping> routeMappings;
    private List<String> tripGpxStrings;
    private List<String> tripCompletedGpxStrings;
    private String tripStaticMapUrl;
    private int routeCount;
    private int completedRouteCount;
    private String error;

    // Simple trip response
    public CompletedTripResponse(ObjectId id, String idString, String tripName) {
        this.id = id;
        this.idString = idString;
        this.tripName = tripName;
    }

    // Trip Response for retrieving related Route info
    public CompletedTripResponse(List<RouteMapping> routeMappings){
        this.routeMappings = routeMappings;
    }

    // Detailed Trip Response
    public CompletedTripResponse(ObjectId id, String idString, String tripName,
                                 String tripDescription, Double tripLength,
                                 Double tripElevationGain, Double tripElevationLoss, Double tripCompletedLength,
                                 Double tripCompletedElevationGain, Double tripCompletedElevationLoss,
                                 Double tripElapsedTime, Double tripMovingTime, Double tripAvgSpeedElapsed,
                                 Double tripAvgSpeedMoving, List<String> tripRouteNames,
                                 List<String> tripCompletedRouteNames, List<String> tripGpxStrings,
                                 List<String> tripCompletedGpxStrings, String tripStaticMapUrl,
                                 int routeCount, int completedRouteCount) {
        this.id = id;
        this.idString = idString;
        this.tripName = tripName;
        this.tripDescription = tripDescription;
        this.tripLength = tripLength;
        this.tripElevationGain = tripElevationGain;
        this.tripElevationLoss = tripElevationLoss;
        this.tripCompletedLength = tripCompletedLength;
        this.tripCompletedElevationGain = tripCompletedElevationGain;
        this.tripCompletedElevationLoss = tripCompletedElevationLoss;
        this.tripElapsedTime = tripElapsedTime;
        this.tripMovingTime = tripMovingTime;
        this.tripAvgSpeedElapsed = tripAvgSpeedElapsed;
        this.tripAvgSpeedMoving = tripAvgSpeedMoving;
        this.tripCompletedRouteNames = tripCompletedRouteNames;
        this.tripRouteNames = tripRouteNames;
        this.tripGpxStrings = tripGpxStrings;
        this.tripCompletedGpxStrings = tripCompletedGpxStrings;
        this.tripStaticMapUrl = tripStaticMapUrl;
        this.routeCount = routeCount;
        this.completedRouteCount = completedRouteCount;
    }

    // Trip Response when an error occurs
    public CompletedTripResponse(String error) {
        this.error = error;
    }

}
