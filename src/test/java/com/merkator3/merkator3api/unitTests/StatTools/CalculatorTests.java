package com.merkator3.merkator3api.unitTests.StatTools;

import static com.merkator3.merkator3api.StatTools.TotalCalculator.*;
import static com.merkator3.merkator3api.StatTools.CompletedTripCalculator.*;

import com.merkator3.merkator3api.StatTools.CompletedTripCalculator;
import org.junit.jupiter.api.*;
import java.util.List;
import java.util.Map;

public class CalculatorTests {

    CompletedTripCalculator compTripCalc = new CompletedTripCalculator();

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
        double distance = 230.6;
        double time = 687.0;
        double avgSpeed = compTripCalc.calculateAvgSpeed(distance, time);
        Assertions.assertEquals(20.1, avgSpeed);
    }
}
