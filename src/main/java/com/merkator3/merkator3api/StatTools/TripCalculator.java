package com.merkator3.merkator3api.StatTools;

import com.merkator3.merkator3api.GpxTools.GpxDistanceCalculator;
import com.merkator3.merkator3api.GpxTools.GpxElevationCalculator;
import com.merkator3.merkator3api.models.route.planned.Route;
import com.merkator3.merkator3api.models.trip.TripMarker;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

public class TripCalculator {

    GpxDistanceCalculator distCalc = new GpxDistanceCalculator();

    GpxElevationCalculator elevCalc = new GpxElevationCalculator();

    public <T extends TripMarker> Double totalDistance(T trip) {
        List<Route> tripRoutes = trip.getTripRoutes();

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

    public <T extends TripMarker> Double totalElevationGain(T trip) {
        List<Route> tripRoutes = trip.getTripRoutes();

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

    public <T extends TripMarker> Double totalElevationLoss(T trip) {
        List<Route> tripRoutes = trip.getTripRoutes();

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
