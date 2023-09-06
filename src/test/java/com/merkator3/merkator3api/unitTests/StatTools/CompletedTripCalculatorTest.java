package com.merkator3.merkator3api.unitTests.StatTools;

import com.merkator3.merkator3api.StatTools.CompletedTripCalculator;
import com.merkator3.merkator3api.models.route.completed.CompletedRoute;

import io.jenetics.jpx.GPX;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Path;

class CompletedTripCalculatorTest {

    CompletedRoute testCompletedRoute;

    CompletedTripCalculator compTripCalc = new CompletedTripCalculator();

    @BeforeEach
    void createCompletedRoute() {
        testCompletedRoute = new CompletedRoute("testCompletedRoute", false);
    }

    @Test
    void testCalculateElapsedTime() throws IOException {
        Path path = Path.of("src/test/TestFiles/CompletedGPX/Heading_up_to_join_dad_for_the_end_of_LEJOG_.gpx");
        testCompletedRoute.setRouteGpxString(path);
        Double totalTime = compTripCalc.calculateElapsedTime(testCompletedRoute);
        Double expectedTime = 435.0;
        Assertions.assertEquals(expectedTime,totalTime);
    }

    @Test
    void testCalculateMovingTime() throws IOException {
        Path path = Path.of("src/test/TestFiles/CompletedGPX/Heading_up_to_join_dad_for_the_end_of_LEJOG_.gpx");
        GPX gpx = GPX.read(path);
        testCompletedRoute.setRouteGpxString(path);
        Double totalTime = compTripCalc.calculateMovingTime(testCompletedRoute);
        Double expectedTime = 391.0;
        Assertions.assertEquals(expectedTime,totalTime);
    }

}