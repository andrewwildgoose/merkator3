package com.merkator3.merkator3api.models;

import io.jenetics.jpx.GPX;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "routes")
public class Route {
    @Id
    private Long id;
    private String routeName;
    private String routeDescription;



    private GPX routeGpx;

    public Route(String routeName) {
        this.routeName = routeName;
    }

    public Long getId() {
        return id;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getRouteDescription() {
        return routeDescription;
    }

    public void setRouteDescription(String routeDescription) {
        this.routeDescription = routeDescription;
    }

    public GPX getRouteGpx() {
        return routeGpx;
    }

    public void setRouteGpx(GPX routeGpx) {
        this.routeGpx = routeGpx;
    }
}