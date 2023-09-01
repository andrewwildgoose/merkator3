package com.merkator3.merkator3api.models.trip;

import com.merkator3.merkator3api.models.route.planned.Route;

import java.util.List;

public interface TripMarker {

    List<Route> getTripRoutes();
}
