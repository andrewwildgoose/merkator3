package com.merkator3.merkator3api.integrationTests;

import com.merkator3.merkator3api.StatTools.TripCalculator;
import com.merkator3.merkator3api.models.route.planned.Route;
import com.merkator3.merkator3api.models.trip.planned.Trip;
import com.merkator3.merkator3api.models.user.MerkatorUser;
import com.merkator3.merkator3api.services.route.RouteService;
import com.merkator3.merkator3api.services.trip.TripService;
import com.merkator3.merkator3api.services.user.UserService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TripIntegrationTests {

    @Autowired
    private TripService tripService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private UserService userService;

    Route testRoute1;
    Route testRoute2;
    Route testRoute3;
    Trip testPlannedTrip1;
    MerkatorUser testUser;



    // create test user, trip & routes
    @BeforeEach
    void createTrip() throws IOException {
        userService.createUser("merkatorUser1@google.com", "1234");
        testUser = userService.findByEmail("merkatorUser1@google.com");
        Path path = Path.of("src/test/TestFiles/GPX/Belgium to Netherlands/2021-10-16_525667120_Brussles to Bruges (Lunch in Ghent).gpx");

        // Set up route 1
        byte[] fileContent1 = Files.readAllBytes(path);
        MockMultipartFile gpxFile1 = new MockMultipartFile(
                "gpxFile1",
                path.getFileName().toString(),
                "application/xml",
                fileContent1
        );

        ObjectId testRoute1Id = routeService.addRoute(testUser.getId(), "Btb1", gpxFile1);

        // Set up route 2
        byte[] fileContent2 = Files.readAllBytes(path);
        MockMultipartFile gpxFile2 = new MockMultipartFile(
                "gpxFile2",
                path.getFileName().toString(),
                "application/xml",
                fileContent2
        );

        ObjectId testRoute2Id = routeService.addRoute(testUser.getId(), "Btb2", gpxFile2);

        // Set up route 3
        byte[] fileContent3 = Files.readAllBytes(path);
        MockMultipartFile gpxFile3 = new MockMultipartFile(
                "gpxFile3",
                path.getFileName().toString(),
                "application/xml",
                fileContent3
        );

        ObjectId testRoute3Id = routeService.addRoute(testUser.getId(), "Btb3", gpxFile3);

        // Set up trip
        ObjectId testPlannedTrip1Id = tripService.addTrip(testUser.getId(), "testTrip1");
        tripService.addRouteToTrip(testPlannedTrip1Id, testRoute1Id);
        tripService.addRouteToTrip(testPlannedTrip1Id, testRoute2Id);
        tripService.addRouteToTrip(testPlannedTrip1Id, testRoute3Id);

        testRoute1 = routeService.getRoute(testRoute1Id);
        testRoute2 = routeService.getRoute(testRoute2Id);
        testRoute3 = routeService.getRoute(testRoute3Id);
        testPlannedTrip1 = tripService.getTrip(testPlannedTrip1Id);

    }

    // remove test user, trip & routes
    @AfterEach
    void cleanUp(){
        userService.deleteRoute(testUser.getId(), testRoute1.getId());
        userService.deleteRoute(testUser.getId(), testRoute2.getId());
        userService.deleteRoute(testUser.getId(),testRoute3.getId());
        userService.deleteTrip(testUser.getId(), testPlannedTrip1.getId());
        userService.deleteUser(testUser.getId());

    }

    @Test
    void testCalculateTripDistance() {
        TripCalculator tripCalculator = new TripCalculator();
        List<Route> tripRoutes = tripService.getTripRoutes(testPlannedTrip1);
        Double tripDist = tripCalculator.totalDistance(tripRoutes);
        Assertions.assertEquals(317.85, tripDist);
    }

    @Test
    void testCalculateTripElevationGain() {
        TripCalculator tripCalculator = new TripCalculator();
        List<Route> tripRoutes = tripService.getTripRoutes(testPlannedTrip1);
        Double tripElevGain = tripCalculator.totalElevationGain(tripRoutes);
        Assertions.assertEquals(975.0, tripElevGain);
    }

    @Test
    void testCalculateTripElevationLoss() {
        TripCalculator tripCalculator = new TripCalculator();
        List<Route> tripRoutes = tripService.getTripRoutes(testPlannedTrip1);
        Double tripElevLoss = tripCalculator.totalElevationLoss(tripRoutes);
        Assertions.assertEquals(1032.0, tripElevLoss);
    }


}
