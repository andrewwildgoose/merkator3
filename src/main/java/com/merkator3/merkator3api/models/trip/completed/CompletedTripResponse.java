package com.merkator3.merkator3api.models.trip.completed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    private String tripElapsedTime;
    private String tripMovingTime;
    private Double tripAvgSpeedElapsed;
    private Double tripAvgSpeedMoving;
    private List<String> tripRouteNames;
    private List<String> tripCompletedRouteNames;
    private List<String> tripGpxStrings;
    private List<List<Integer>> tripRouteColours;
    private List<String> tripCompletedGpxStrings;
    private List<List<Integer>> tripCompletedRouteColours;
    private String tripStaticMapUrl;
    private int routeCount;
    private int completedRouteCount;
    private String error;

    // Simple trip response
    public CompletedTripResponse(ObjectId id, String idString, String tripName, String tripDescription,
                                 Double tripLength, int routeCount, Double tripCompletedLength,
                                 int completedRouteCount, String tripStaticMapUrl) {
        this.id = id;
        this.idString = idString;
        this.tripName = tripName;
        this.tripDescription = tripDescription;
        this.tripLength = tripLength;
        this.routeCount = routeCount;
        this.tripCompletedLength = tripCompletedLength;
        this.completedRouteCount = completedRouteCount;
        this.tripStaticMapUrl = tripStaticMapUrl;
    }


    // Detailed Trip Response
    public CompletedTripResponse(ObjectId id, String idString, String tripName,
                                 String tripDescription, Double tripLength,
                                 Double tripElevationGain, Double tripElevationLoss, Double tripCompletedLength,
                                 Double tripCompletedElevationGain, Double tripCompletedElevationLoss,
                                 String tripElapsedTime, String tripMovingTime, Double tripAvgSpeedElapsed,
                                 Double tripAvgSpeedMoving, List<String> tripRouteNames,
                                 List<String> tripCompletedRouteNames, List<String> tripGpxStrings,
                                 List<List<Integer>> tripRouteColours,
                                 List<String> tripCompletedGpxStrings, List<List<Integer>> tripCompletedRouteColours,
                                 String tripStaticMapUrl, int routeCount, int completedRouteCount) {
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
        this.tripRouteColours = tripRouteColours;
        this.tripCompletedGpxStrings = tripCompletedGpxStrings;
        this.tripCompletedRouteColours = tripCompletedRouteColours;
        this.tripStaticMapUrl = tripStaticMapUrl;
        this.routeCount = routeCount;
        this.completedRouteCount = completedRouteCount;
    }

    // Trip Response when an error occurs
    public CompletedTripResponse(String error) {
        this.error = error;
    }

}
