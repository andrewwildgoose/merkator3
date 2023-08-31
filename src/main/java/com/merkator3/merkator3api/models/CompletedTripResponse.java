package com.merkator3.merkator3api.models;

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
    private Double tripAvgSpeed;
    private List<String> tripRouteNames;
    private List<RouteMapping> routeMappings;
    private List<String> tripGpxStrings;
    private String tripStaticMapUrl;
    private int routeCount;
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
                               Double tripElevationGain, Double tripElevationLoss, Double tripAvgSpeed, List<String> tripRouteNames,
                               List<String> tripGpxStrings, String tripStaticMapUrl, int routeCount) {
        this.id = id;
        this.idString = idString;
        this.tripName = tripName;
        this.tripDescription = tripDescription;
        this.tripLength = tripLength;
        this.tripElevationGain = tripElevationGain;
        this.tripElevationLoss = tripElevationLoss;
        this.tripAvgSpeed = tripAvgSpeed;
        this.tripGpxStrings = tripGpxStrings;
        this.tripRouteNames = tripRouteNames;
        this.tripStaticMapUrl = tripStaticMapUrl;
        this.routeCount = routeCount;
    }

    // Trip Response when an error occurs
    public CompletedTripResponse(String error) {
        this.error = error;
    }

}
