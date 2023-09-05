package com.merkator3.merkator3api.StatTools;

import com.merkator3.merkator3api.GpxTools.GpxDistanceCalculator;
import com.merkator3.merkator3api.GpxTools.GpxElevationCalculator;
import com.merkator3.merkator3api.models.route.RouteMarker;
import com.merkator3.merkator3api.models.route.planned.Route;
import com.merkator3.merkator3api.models.trip.TripMarker;
import com.merkator3.merkator3api.services.trip.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

public class TripCalculator {


    GpxDistanceCalculator distCalc = new GpxDistanceCalculator();

    GpxElevationCalculator elevCalc = new GpxElevationCalculator();

    public <R extends RouteMarker> Double totalDistance(List<R> tripRoutes) {

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

    public <R extends RouteMarker> Double totalElevationGain(List<R> tripRoutes) {

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

    public <R extends RouteMarker> Double totalElevationLoss(List<R> tripRoutes) {

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
