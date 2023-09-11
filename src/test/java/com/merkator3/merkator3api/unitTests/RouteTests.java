package com.merkator3.merkator3api.unitTests;

import com.merkator3.merkator3api.models.route.planned.Route;
import com.merkator3.merkator3api.services.route.RouteService;
import io.jenetics.jpx.GPX;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.io.IOException;
import java.nio.file.Path;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RouteTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RouteService routeService;


    Route testRoute;

    @Value("${merkator.api.mapBoxKey}")
    private String mapBoxKey;

    @BeforeEach
    void createRoute(){
        testRoute = new Route("testRoute");
    }

    @Test
    void testRouteCreated() {
        Assertions.assertEquals(testRoute.getRouteName(), "testRoute");
    }

    @Test
    void testRouteDescription() {
        String testDescription = "test description";
        testRoute.setRouteDescription(testDescription);
        Assertions.assertEquals(testRoute.getRouteDescription(), testDescription);
    }

    @Test
    void testGPXAdded() throws IOException {
        Path path = Path.of("src/test/TestFiles/GPX/London to Brighton Return.gpx");
        GPX routeGPX = GPX.read(path);
        testRoute.setRouteGpxString(routeGPX);
        Assertions.assertEquals(routeGPX.getTracks().toString(), testRoute.getRouteGpx().getTracks().toString());
    }

    // Test to ensure valid URL for static map image is generated
    // NB This test will only work if the application is running due to external connection requirement
    @Test
    void testRouteStaticMapUrl() throws IOException {
        Path path = Path.of("src/test/TestFiles/GPX/Belgium to Netherlands/2021-10-16_525667120_Brussles to Bruges (Lunch in Ghent).gpx");
        GPX routeGPX = GPX.read(path);
        testRoute.setRouteGpxString(routeGPX);
        routeService.setRouteStaticMapUrl(testRoute, mapBoxKey);
        String mapUrl = testRoute.getRouteStaticMapURL();
        String expectedStart = "https://api.mapbox.com/styles/v1/merkator1/cllax8yio00sf01pdfsrn1gmo/static/";
        Assertions.assertTrue(mapUrl.startsWith(expectedStart));
    }
}