package com.merkator3.merkator3api.StatTools;

import com.merkator3.merkator3api.GpxTools.GpxDistanceCalculator;
import com.merkator3.merkator3api.GpxTools.GpxElevationCalculator;
import com.merkator3.merkator3api.models.route.completed.CompletedRoute;

import io.jenetics.jpx.GPX;
import io.jenetics.jpx.WayPoint;
import org.apache.commons.math3.util.Precision;


import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.merkator3.merkator3api.GpxTools.GpxReader.gpxToPointList;


/* Used to calculate the average speed of a completed
trip from a map of the distances and time (in mins)
for a stage */

public class CompletedTripCalculator {

    final GpxDistanceCalculator distCalc = new GpxDistanceCalculator();

    final GpxElevationCalculator elevCalc = new GpxElevationCalculator();

    public Double totalCompletedDistance(List<CompletedRoute> tripCompletedRoutes) {

        double totalCompletedDistance = tripCompletedRoutes.stream()
                .mapToDouble(completedRoute -> {
                    try {
                        return distCalc.lengthToKm(distCalc.calculateDistance(completedRoute.getRouteGpx()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .sum();

        // Format the total distance to two decimal places
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String formattedTotalDistance = decimalFormat.format(totalCompletedDistance);

        // Parse the formatted string back to a double
        return Double.parseDouble(formattedTotalDistance);
    }

    public Double totalCompletedElevationGain(List<CompletedRoute> tripCompletedRoutes) {

        return tripCompletedRoutes.stream()
                .mapToDouble(completedRoute -> {
                    try {
                        return elevCalc.calculateElevationGain(completedRoute.getRouteGpx());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .sum();
    }

    public Double totalCompletedElevationLoss(List<CompletedRoute> tripCompletedRoutes) {

        return tripCompletedRoutes.stream()
                .mapToDouble(completedRoute -> {
                    try {
                        return elevCalc.calculateElevationLoss(completedRoute.getRouteGpx());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .sum();
    }

    // Calculate the elapsed time for a completed route.
    public double calculateElapsedTime(CompletedRoute route) throws IOException {
        GPX gpx = route.getRouteGpx();
        Duration totalDuration = Duration.ZERO;

        List<WayPoint> wayPoints = gpxToPointList(gpx);

        WayPoint previousWaypoint = null;

        for (WayPoint wayPoint : wayPoints) {
            if (wayPoint.getTime().isPresent()) {
                // Calculate the duration between waypoints and add it to the total duration
                if (previousWaypoint != null) {
                    totalDuration = totalDuration.plus(Duration.between(previousWaypoint.getTime().get(), wayPoint.getTime().get()));
                }
                previousWaypoint = wayPoint; // Update the previous waypoint
            }
        }



        return totalDuration.toMinutes();
    }

    // Calculate the moving time for a completed route.
    public double calculateMovingTime(CompletedRoute route) throws IOException {
        GPX gpx = route.getRouteGpx();
        Duration totalDuration = Duration.ZERO;

        List<WayPoint> wayPoints = gpxToPointList(gpx);
        WayPoint previousWaypoint = null; // Initialize the previous waypoint for time calculation

        for (WayPoint wayPoint : wayPoints) {
            Optional<Instant> time = wayPoint.getTime();
            if (time.isPresent()) {
                if (previousWaypoint != null) {
                    if (!wayPoint.getLatitude().equals(previousWaypoint.getLatitude()) ||
                            !wayPoint.getLongitude().equals(previousWaypoint.getLongitude())) {
                        // Calculate the duration between waypoints with changes in location and add it to the total duration
                        if (previousWaypoint.getTime().isPresent()) {
                            Duration duration = Duration.between(previousWaypoint.getTime().get(), time.get());
                            totalDuration = totalDuration.plus(duration);
                        }
                    }
                }
                previousWaypoint = wayPoint;
            }
        }
        return totalDuration.toMinutes();
    }

    public double calculateTripElapsedTime(List<CompletedRoute> tripCompletedRoutes) {
        return tripCompletedRoutes.stream()
                .mapToDouble(route -> {
                    try {
                        return calculateElapsedTime(route);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return 0;
                    }
                })
                .sum();
    }

    public double calculateTripMovingTime(List<CompletedRoute> tripCompletedRoutes) {
        return tripCompletedRoutes.stream()
                .mapToDouble(route -> {
                    try {
                        return calculateMovingTime(route);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return 0;
                    }
                })
                .sum();
    }


    public double calculateAvgSpeed(Double distance, Double time) {
        Double decimalTime = time / 60;
        return Precision.round(distance / decimalTime, 1);
    }

}
