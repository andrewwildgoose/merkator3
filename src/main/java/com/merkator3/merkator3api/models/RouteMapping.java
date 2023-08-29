package com.merkator3.merkator3api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RouteMapping {

    private String routeId;
    private String routeName;
    private String error;

    public RouteMapping(String routeId, String routeName) {
        this.routeId = routeId;
        this.routeName = routeName;
    }

    public RouteMapping(String error) {
        this.error = error;
    }

    public String getRouteId() {
        return routeId;
    }
}
