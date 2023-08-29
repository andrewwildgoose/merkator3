package com.merkator3.merkator3api.StatTools;

import com.merkator3.merkator3api.GpxTools.GpxDistanceCalculator;
import com.merkator3.merkator3api.GpxTools.GpxElevationCalculator;
import com.merkator3.merkator3api.models.Route;
import com.merkator3.merkator3api.models.PlannedTrip;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

public class TripCalculator {

    public Double totalDistance(PlannedTrip plannedTrip) {
        List<Route> tripRoutes = plannedTrip.getTripRoutes();
        GpxDistanceCalculator distCalc = new GpxDistanceCalculator();

        double totalDistance = tripRoutes.stream()
                .mapToDouble(route -> {
                    try {
                        return distCalc.lengthToKm(distCalc.calculateDistance(route.getRouteGpx()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .sum();

        // Format the total distance to two decimal places
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String formattedTotalDistance = decimalFormat.format(totalDistance);

        // Parse the formatted string back to a double
        return Double.parseDouble(formattedTotalDistance);
    }
    public Double totalElevationGain(PlannedTrip plannedTrip) {
        List<Route> tripRoutes = plannedTrip.getTripRoutes();
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

    public Double totalElevationLoss(PlannedTrip plannedTrip) {
        List<Route> tripRoutes = plannedTrip.getTripRoutes();
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
