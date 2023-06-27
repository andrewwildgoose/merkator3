package com.merkator3.merkator3api.unitTests;

import com.merkator3.merkator3api.StatTools.TripCalculator;
import com.merkator3.merkator3api.models.Route;
import com.merkator3.merkator3api.models.Trip;
import io.jenetics.jpx.GPX;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

//@SpringBootTest
public class TripTests {

    Route testRoute1;
    Route testRoute2;
    Route testRoute3;
    Trip testTrip1;

    @BeforeEach
    void createTrip() throws IOException {
        testRoute1 = new Route("testRoute1");
        Path path1 = Path.of("src/test/TestFiles/GPX/Belgium to Netherlands/2021-10-16_525667120_Brussles to Bruges (Lunch in Ghent).gpx");
        testRoute1.setRouteGpx(GPX.read(path1));

        testRoute2 = new Route("testRoute2");
        Path path2 = Path.of("src/test/TestFiles/GPX/Belgium to Netherlands/2021-10-16_525676347_Bruges to Antwerp.gpx");
        testRoute2.setRouteGpx(GPX.read((path2)));

        testRoute3 = new Route("testRoute3");
        Path path3 = Path.of("src/test/TestFiles/GPX/Belgium to Netherlands/2021-10-16_525668657_Antwerp to Rotterdam.gpx");
        testRoute3.setRouteGpx(GPX.read(path3));

        testTrip1 = new Trip("testTrip1");
        List<Route> testRouteList = List.of(testRoute1, testRoute2, testRoute3);
        testTrip1.setTripRoutes(testRouteList);
    }

    @Test
    void testCalculateTripDistance() {
        TripCalculator tripCalculator = new TripCalculator(testTrip1);
        Double tripDist = tripCalculator.totalDistance();
        Assertions.assertEquals(347.17, tripDist);
    }

    @Test
    void testCalculateTripElevationGain() {
        TripCalculator tripCalculator = new TripCalculator(testTrip1);
        Double tripElevGain = tripCalculator.totalElevationGain();
        Assertions.assertEquals(660, tripElevGain);
    }

    @Test
    void testCalculateTripElevationLoss() {
        TripCalculator tripCalculator = new TripCalculator(testTrip1);
        Double tripElevLoss = tripCalculator.totalElevationLoss();
        Assertions.assertEquals(684.0, tripElevLoss);
    }


}
