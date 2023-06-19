package com.merkator3.merkator3api.StatTools;

import org.apache.commons.math3.util.Precision;

import java.util.Map;


/* Used to calculate the average speed of a completed
trip from a map of the distances and time (in mins)
for a stage */

public class AverageSpeedCalculator {

    public static double calculateAvgSpeed (Map<Double, Double> stages) {
        Double distances = stages.keySet().stream()
                .mapToDouble(dist -> dist)
                .sum();
        Double times = stages.values().stream()
                .mapToDouble(time -> time)
                .sum();

        Double decimalTime = times / 60;

        return Precision.round(distances / decimalTime, 1);
    }

}
