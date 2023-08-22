package com.merkator3.merkator3api.models;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.List;

@Data
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
    private List<String> tripGpxStrings;
    private String tripStaticMapUrl;

    public TripResponse(ObjectId id, String idString, String tripName) {
        this.id = id;
        this.idString = idString;
        this.tripName = tripName;
    }
    public TripResponse(ObjectId id, String idString, String tripName,
                        String tripDescription, Double tripLength,
                        Double tripElevationGain, Double tripElevationLoss, List<String> tripRouteNames,
                        List<String> tripGpxStrings, String tripStaticMapUrl) {
        this.id = id;
        this.idString = idString;
        this.tripName = tripName;
        this.tripDescription = tripDescription;
        this.tripLength = tripLength;
        this.tripElevationGain = tripElevationGain;
        this.tripElevationLoss = tripElevationLoss;
        this.tripGpxStrings = tripGpxStrings;
        this.tripRouteNames = tripRouteNames;
        this.tripStaticMapUrl = tripStaticMapUrl;
    }
}