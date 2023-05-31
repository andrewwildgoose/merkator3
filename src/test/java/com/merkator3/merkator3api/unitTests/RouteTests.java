package com.merkator3.merkator3api.unitTests;

import com.merkator3.merkator3api.models.Route;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RouteTests {

    @Test
    void routeCreated() {

        Route testRoute = new Route("testRoute");
        Assertions.assertEquals(testRoute.getRouteName(), "testRoute");

    }


}