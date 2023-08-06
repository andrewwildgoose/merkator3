package com.merkator3.merkator3api.unitTests;

import com.merkator3.merkator3api.models.Route;
import io.jenetics.jpx.GPX;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Path;

@SpringBootTest
public class RouteTests {

    Route testRoute;

    @BeforeEach
    void createRoute(){
        testRoute = new Route("testRoute");
    }

    @Test
    void testRouteCreated() {
        Assertions.assertEquals(testRoute.getRouteName(), "testRoute");

    }

    @Test
    void testGPXAdded() throws IOException {
        Path path = Path.of("src/test/TestFiles/GPX/London to Brighton Return.gpx");
        GPX routeGPX = GPX.read(path);
        testRoute.setRouteGpx(routeGPX);
        System.out.println(testRoute.getRouteGpxString());
        Assertions.assertEquals(routeGPX.getTracks().toString(), testRoute.getRouteGpx().getTracks().toString());
    }


}