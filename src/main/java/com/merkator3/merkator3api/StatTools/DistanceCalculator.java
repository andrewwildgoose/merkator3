package com.merkator3.merkator3api.StatTools;

import java.util.List;

public class DistanceCalculator {

    public static double calculateTotalDistance(List<Double> distances) {
        double totalDistance = 0.0;

        for (double distance : distances) {
            totalDistance += distance;
        }

        return totalDistance;
    }

    public String formatTotalDistance(Double totalDistance, String unitType){
        return totalDistance.toString() + " " + unitType;

    }

}
