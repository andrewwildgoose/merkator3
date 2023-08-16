package com.merkator3.merkator3api.models;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@Builder
public class TripResponse {
    private ObjectId id;
    private String tripName;
    private String tripDescription;
    private Double tripLength;
    private Double tripElevationGain;
    private Double tripElevationLoss;
    private List<String> tripRouteNames;
    private List<String> tripGpxStrings;

    public TripResponse(ObjectId id, String tripName) {
        this.id = id;
        this.tripName = tripName;
    }
    public TripResponse(ObjectId id, String tripName,
                        String tripDescription, Double tripLength,
                        Double tripElevationGain, Double tripElevationLoss, List<String> tripRouteNames,
                        List<String> tripGpxStrings) {
        this.id = id;
        this.tripName = tripName;
        this.tripDescription = tripDescription;
        this.tripLength = tripLength;
        this.tripElevationGain = tripElevationGain;
        this.tripElevationLoss = tripElevationLoss;
        this.tripGpxStrings = tripGpxStrings;
        this.tripRouteNames = tripRouteNames;
    }
}