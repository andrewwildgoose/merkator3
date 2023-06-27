package com.merkator3.merkator3api.StatTools;

import com.merkator3.merkator3api.GpxTools.GpxDistanceCalculator;
import com.merkator3.merkator3api.GpxTools.GpxElevationCalculator;
import com.merkator3.merkator3api.models.Route;
import com.merkator3.merkator3api.models.Trip;

import java.util.List;

public class TripCalculator {

    private final Trip trip;
    private final List<Route> tripRoutes;

    public TripCalculator(Trip trip) {
        this.trip = trip;
        this.tripRoutes = trip.getTripRoutes();
    }
    public Double totalDistance() {
        GpxDistanceCalculator distCalc = new GpxDistanceCalculator();

        return tripRoutes.stream()
                .mapToDouble(route -> distCalc.lengthToKm(distCalc.calculateDistance(route.getRouteGpx())))
                .sum();
    }

    public Double totalElevationGain() {
        GpxElevationCalculator elevCalc = new GpxElevationCalculator();
        return tripRoutes.stream()
                .mapToDouble(route -> elevCalc.calculateElevationGain(route.getRouteGpx()))
                .sum();
    }

    public Double totalElevationLoss() {
        GpxElevationCalculator elevCalc = new GpxElevationCalculator();
        return tripRoutes.stream()
                .mapToDouble(route -> elevCalc.calculateElevationLoss(route.getRouteGpx()))
                .sum();
    }
}
