package com.merkator3.merkator3api.StatTools;

import com.merkator3.merkator3api.models.Route;
import com.merkator3.merkator3api.models.Trip;

import java.util.List;

public class TripCalculator {

    private final Trip trip;

    TripCalculator(Trip trip){
        this.trip = trip;
    }
    public static Double totalDistance(){
        List<Route> tripRoutes = trip.getTripRoutes();
    }
}
