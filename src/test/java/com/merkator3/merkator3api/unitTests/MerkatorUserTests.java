package com.merkator3.merkator3api.unitTests;


import com.merkator3.merkator3api.models.MerkatorUser;
import com.merkator3.merkator3api.models.Route;
import com.merkator3.merkator3api.models.Trip;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class MerkatorUserTests {

    MerkatorUser testMerkatorUser;
    @BeforeEach
    void createUser() {
        testMerkatorUser = new MerkatorUser("testUser");
    }

    @Test
    void testUserCreated() {
        Assertions.assertEquals(testMerkatorUser.getUsername(), "testUser");
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
        Trip testTrip = new Trip("testTrip");
        testMerkatorUser.addTrip(testTrip.getId());
        Assertions.assertEquals(testTrip.getId(), testMerkatorUser.getUserTrips().get(0));
    }

}
