package com.merkator3.merkator3api.unitTests;


import com.merkator3.merkator3api.models.user.MerkatorUser;
import com.merkator3.merkator3api.models.route.planned.Route;
import com.merkator3.merkator3api.models.trip.planned.Trip;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class MerkatorUserTests {

    MerkatorUser testMerkatorUser;
    @BeforeEach
    void createUser() {
        testMerkatorUser = new MerkatorUser("testUser@google.com", "testUser");
    }

    // Test to verify user created by retrieving the correct email
    @Test
    void testUserCreated() {
        Assertions.assertEquals(testMerkatorUser.getUsername(), "testUser@google.com");
    }

    // Test to verify retrieval of user's name
    @Test
    void testGetName() {
        Assertions.assertEquals(testMerkatorUser.getName(), "testUser");
    }

    @Test
    void testUserRouteAdded() {
        Route testRoute = new Route("testRoute");
        testMerkatorUser.addRoute(testRoute.getId());
        Assertions.assertEquals(testRoute.getId(), testMerkatorUser.getUserRoutes().get(0));
    }

    @Test
    void testUserTripAdded() {
        Route testRoute1 = new Route("testRoute1");
        Route testRoute2 = new Route("testRoute2");
        Trip testPlannedTrip = new Trip("testTrip");
        testMerkatorUser.addTrip(testPlannedTrip.getId());
        Assertions.assertEquals(testPlannedTrip.getId(), testMerkatorUser.getUserTrips().get(0));
    }

}
