package com.merkator3.merkator3api.unitTests;


import com.merkator3.merkator3api.models.Route;
import com.merkator3.merkator3api.models.Trip;
import com.merkator3.merkator3api.models.User;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class UserTests {

    User testUser;
    @BeforeEach
    void createUser() {
        testUser = new User("testUser");
    }

    @Test
    void testUserCreated() {
        Assertions.assertEquals(testUser.getUserName(), "testUser");
    }

    @Test
    void testUserRouteAdded() {
        Route testRoute = new Route("testRoute");
        testUser.addRoute(testRoute.getId());
        Assertions.assertEquals(testRoute.getId(), testUser.getUserRoutes().get(0));
    }

    @Test
    void testUserTripAdded() {
        Route testRoute1 = new Route("testRoute1");
        Route testRoute2 = new Route("testRoute2");
        Trip testTrip = new Trip("testTrip");
        testUser.addTrip(testTrip.getId());
        Assertions.assertEquals(testTrip.getId(), testUser.getUserTrips().get(0));
    }

}
