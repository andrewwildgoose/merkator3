package com.merkator3.merkator3api.StatTools;

import com.merkator3.merkator3api.GpxTools.GpxDistanceCalculator;
import com.merkator3.merkator3api.models.Route;
import com.merkator3.merkator3api.models.Trip;

import java.util.List;

public class TripCalculator {

    private final Trip trip;

    TripCalculator(Trip trip){
        this.trip = trip;
    }
    public Double totalDistance(){
        List<Route> tripRoutes = trip.getTripRoutes();
        GpxDistanceCalculator distCalc = new GpxDistanceCalculator();

        return tripRoutes.stream()
                .mapToDouble(route -> distCalc.lengthToKm(distCalc.calculateDistance(route.getRouteGpx())))
                .sum();
    }
}
