package com.merkator3.merkator3api.models.route.planned;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
@Builder
public class RouteResponse {
    private ObjectId id;
    private String idString;
    private String routeName;
    private String routeDescription;
    private Double routeLength;
    private int routeElevationGain;
    private int routeElevationLoss;
    private String routeGpxString;
    private String routeStaticMapUrl;

    public RouteResponse(ObjectId id, String idString, String routeName,
                            String routeDescription, Double routeLength,
                            int routeElevationGain, int routeElevationLoss,
                            String routeGpxString, String routeStaticMapUrl) {
        this.id = id;
        this.idString = idString;
        this.routeName = routeName;
        this.routeDescription = routeDescription;
        this.routeLength = routeLength;
        this.routeElevationGain = routeElevationGain;
        this.routeElevationLoss = routeElevationLoss;
        this.routeGpxString = routeGpxString;
        this.routeStaticMapUrl = routeStaticMapUrl;
    }

}