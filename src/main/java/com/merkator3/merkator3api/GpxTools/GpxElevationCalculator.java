package com.merkator3.merkator3api.GpxTools;

import io.jenetics.jpx.*;
import org.apache.commons.math3.util.Precision;

import java.util.Optional;

public class GpxElevationCalculator {
    public int calculateElevationGain(GPX gpx) {
        int[] totalElevationGain = {0};
        gpx.tracks()
                .flatMap(Track::segments)
                .forEach(segment -> {
                    WayPoint[] previousWaypoint = {null};
                    segment.points()
                            .filter(waypoint -> waypoint.getElevation().isPresent())
                            .forEach(waypoint -> {
                                if (previousWaypoint[0] != null) {
                                    int elevationDifference = waypoint.getElevation().get().intValue() - previousWaypoint[0].getElevation().get().intValue();
                                    if (elevationDifference > 0) {
                                        totalElevationGain[0] += elevationDifference;
                                    }
                                }
                                previousWaypoint[0] = waypoint;
                            });
                });
        return totalElevationGain[0];
    }

    public int calculateElevationLoss(GPX gpx) {
        int[] totalElevationLoss = {0};
        gpx.tracks()
                .flatMap(Track::segments)
                .forEach(segment -> {
                    WayPoint[] previousWaypoint = {null};
                    segment.points()
                            .filter(waypoint -> waypoint.getElevation().isPresent())
                            .forEach(waypoint -> {
                                if (previousWaypoint[0] != null) {
                                    int elevationDifference = waypoint.getElevation().get().intValue() - previousWaypoint[0].getElevation().get().intValue();
                                    if (elevationDifference < 0) {
                                        totalElevationLoss[0] += elevationDifference;
                                    }
                                }
                                previousWaypoint[0] = waypoint;
                            });
                });
        return totalElevationLoss[0] * -1;
    }

    public Double lengthToM(Length elevation) {
        return Precision.round(elevation.doubleValue(), 2);
    }
}
