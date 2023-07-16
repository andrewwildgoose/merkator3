package com.merkator3.merkator3api.StatTools;

import com.merkator3.merkator3api.GpxTools.GpxDistanceCalculator;
import com.merkator3.merkator3api.GpxTools.GpxElevationCalculator;
import com.merkator3.merkator3api.models.Route;
import com.merkator3.merkator3api.models.Trip;

import java.io.IOException;
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
                .mapToDouble(route -> {
                    try {
                        return distCalc.lengthToKm(distCalc.calculateDistance(route.getRouteGpx()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .sum();
    }

    public Double totalElevationGain() {
        GpxElevationCalculator elevCalc = new GpxElevationCalculator();
        return tripRoutes.stream()
                .mapToDouble(route -> {
                    try {
                        return elevCalc.calculateElevationGain(route.getRouteGpx());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .sum();
    }

    public Double totalElevationLoss() {
        GpxElevationCalculator elevCalc = new GpxElevationCalculator();
        return tripRoutes.stream()
                .mapToDouble(route -> {
                    try {
                        return elevCalc.calculateElevationLoss(route.getRouteGpx());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .sum();
    }
}
