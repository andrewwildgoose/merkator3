package com.merkator3.merkator3api.unitTests;

import com.merkator3.merkator3api.models.Route;
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
    void testGPXAdded() throws IOException {
        Path path = Path.of("src/test/TestFiles/GPX/London to Brighton Return.gpx");
        GPX routeGPX = GPX.read(path);
        testRoute.setRouteGpxString(routeGPX);
        Assertions.assertEquals(routeGPX.getTracks().toString(), testRoute.getRouteGpx().getTracks().toString());
    }

    // Will show as failed due to random colour generation.
    @Test
    void testRouteStaticMapUrl() throws IOException {
        Path path = Path.of("src/test/TestFiles/GPX/Belgium to Netherlands/2021-10-16_525667120_Brussles to Bruges (Lunch in Ghent).gpx");
        GPX routeGPX = GPX.read(path);
        testRoute.setRouteGpxString(routeGPX);
        testRoute.setRouteStaticMapUrl(mapBoxKey);
        String mapUrl = testRoute.getRouteStaticMapURL(mapBoxKey);
        String expected = "https://api.mapbox.com/styles/v1/merkator1/cllax8yio00sf01pdfsrn1gmo/static/path-6.0+4F2D84-0.9(cyguHm%7BmY%7DvMl%7DYmvEbzg@emNdgWsbLpdj@xEhlV%7BcIpyk@u%7CKlbL)/auto/150x150?access_token=pk.eyJ1IjoibWVya2F0b3IxIiwiYSI6ImNsbGRrcjFsejBiN3EzY3RqM3hudHRqYXEifQ.bIvh-hUNgOcFiOoq7qaHew";
        Assertions.assertEquals(expected, mapUrl);

    }


}