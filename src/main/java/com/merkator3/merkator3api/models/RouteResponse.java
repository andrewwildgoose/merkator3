package com.merkator3.merkator3api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class RouteResponse {
    private String routeName;
    private String routeDescription;
    private String routeGpxString;

    public RouteResponse(String routeName, String routeDescription, String routeGpxString) {
        this.routeName = routeName;
        this.routeDescription = routeDescription;
        this.routeGpxString = routeGpxString;
    }

}