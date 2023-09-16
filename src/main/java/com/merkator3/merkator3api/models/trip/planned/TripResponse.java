package com.merkator3.merkator3api.models.trip.planned;

import com.merkator3.merkator3api.models.route.planned.RouteMapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Representations of different responses that can be sent
 * to the front end when requesting data about a planned Trip.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TripResponse {
    private ObjectId id;
    private String idString;
    private String tripName;
    private String tripDescription;
    private Double tripLength;
    private Double tripElevationGain;
    private Double tripElevationLoss;
    private List<String> tripRouteNames;
    private List<RouteMapping> routeMappings;
    private List<String> tripGpxStrings;
    private List<List<Integer>> tripRouteColours;
    private List<String> tripRouteIds;
    private String tripStaticMapUrl;
    private int routeCount;
    private String error;

    // Simple trip response
    public TripResponse(ObjectId id, String idString, String tripName) {
        this.id = id;
        this.idString = idString;
        this.tripName = tripName;
    }

    // Trip Response for retrieving related Route info
    public TripResponse(List<RouteMapping> routeMappings){
        this.routeMappings = routeMappings;
    }

    // Detailed Trip Response
    public TripResponse(ObjectId id, String idString, String tripName,
                        String tripDescription, Double tripLength,
                        Double tripElevationGain, Double tripElevationLoss, List<String> tripRouteNames,
                        List<String> tripGpxStrings, List<List<Integer>> tripRouteColours, List<String> tripRouteIds, String tripStaticMapUrl, int routeCount) {
        this.id = id;
        this.idString = idString;
        this.tripName = tripName;
        this.tripDescription = tripDescription;
        this.tripLength = tripLength;
        this.tripElevationGain = tripElevationGain;
        this.tripElevationLoss = tripElevationLoss;
        this.tripGpxStrings = tripGpxStrings;
        this.tripRouteColours = tripRouteColours;
        this.tripRouteNames = tripRouteNames;
        this.tripRouteIds = tripRouteIds;
        this.tripStaticMapUrl = tripStaticMapUrl;
        this.routeCount = routeCount;
    }

    // Trip Resonse when an error occurs
    public TripResponse(String error) {
        this.error = error;
    }

}