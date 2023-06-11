package com.merkator3.merkator3api.unitTests;

import com.merkator3.merkator3api.StatTools.DistanceCalculator;
import org.junit.jupiter.api.*;
import java.util.List;
public class DistanceCalculatorTests {

    @Test
    void totalDistanceCalculatorTest(){
        List<Double> distanceList = List.of(5.0, 3.2, 7.8, 2.5, 4.1);
        DistanceCalculator distanceCalculator = new DistanceCalculator();
        double totalDistance = distanceCalculator.calculateTotalDistance(distanceList);
        Assertions.assertEquals(22.6, totalDistance);
    }

    @Test
    void totalDistanceFormatTest(){
        List<Double> distanceList = List.of(5.0, 3.2, 7.8, 2.5, 4.1);
        DistanceCalculator distanceCalculator = new DistanceCalculator();
        double totalDistance = distanceCalculator.calculateTotalDistance(distanceList);
        String formattedTotalDistance = distanceCalculator.formatTotalDistance(totalDistance, "km");
        Assertions.assertEquals("22.6 km", formattedTotalDistance);
    }
}
