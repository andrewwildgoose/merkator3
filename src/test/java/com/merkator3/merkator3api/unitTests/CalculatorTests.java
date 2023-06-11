package com.merkator3.merkator3api.unitTests;

import static com.merkator3.merkator3api.StatTools.TotalCalculator.*;
import static com.merkator3.merkator3api.StatTools.AverageSpeedCalculator.*;
import org.junit.jupiter.api.*;
import java.util.List;
import java.util.Map;

public class CalculatorTests {

    @Test
    void totalCalculatorTest(){
        List<Double> distanceList = List.of(5.0, 3.2, 7.8, 2.5, 4.1);

        double totalDistance = calculateTotal(distanceList);
        Assertions.assertEquals(22.6, totalDistance);
    }

    @Test
    void totalDistanceFormatTest(){
        List<Double> distanceList = List.of(5.0, 3.2, 7.8, 2.5, 4.1);
        double totalDistance = calculateTotal(distanceList);
        String formattedTotalDistance = formatTotal(totalDistance, "km");
        Assertions.assertEquals("22.6 km", formattedTotalDistance);
    }

    @Test
    void averageSpeedCalculatorTest(){
        Map<Double, Double> distAndTimes = Map.of(70.0, 203.0, 85.2, 287.0, 75.4, 197.0);
        double avgSpeed = calculateAvgSpeed(distAndTimes);
        Assertions.assertEquals(20.1, avgSpeed);
    }
}
