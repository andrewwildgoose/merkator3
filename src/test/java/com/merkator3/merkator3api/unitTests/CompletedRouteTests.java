package com.merkator3.merkator3api.unitTests;

import com.merkator3.merkator3api.models.route.completed.CompletedRoute;
import com.merkator3.merkator3api.models.route.planned.Route;
import com.merkator3.merkator3api.services.route.CompletedRouteService;
import io.jenetics.jpx.GPX;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Path;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompletedRouteTests {

    @Autowired
    private CompletedRouteService completedRouteService;

    CompletedRoute testCompletedRoute;

    @BeforeEach
    void createRoute(){
        testCompletedRoute = new CompletedRoute("testRoute", false);
    }

    @Test
    void  testGetRouteId() {
        Assertions.assertEquals(testCompletedRoute.getId().getClass(), ObjectId.class);
    }

    @Test
    void testRouteCreated() {
        Assertions.assertEquals(testCompletedRoute.getRouteName(), "testRoute");
    }

    // Test to ensure the completed route's GpxString is correctly set and returned.
    @Test
    void testSetRouteGpxString() throws IOException {
        Path path = Path.of("src/test/TestFiles/CompletedGPX/Heading_up_to_join_dad_for_the_end_of_LEJOG_.gpx");
        testCompletedRoute.setRouteGpxString(path);

        String actualRouteGpxString = testCompletedRoute.getRouteGpxString();

        String expectedSubstring = "<?xml version=\"1.0\" encoding=\"UTF-";

        int substringLength = expectedSubstring.length();
        String actualSubstring = actualRouteGpxString.substring(0, Math.min(substringLength, actualRouteGpxString.length()));

        Assertions.assertEquals(expectedSubstring, actualSubstring);
    }

    // Test to ensure completed route returns a GPX object as required by calculator functions.
    @Test
    void testGetRouteGpx() throws IOException {
        Path path = Path.of("src/test/TestFiles/CompletedGPX/Heading_up_to_join_dad_for_the_end_of_LEJOG_.gpx");
        testCompletedRoute.setRouteGpxString(path);
        GPX gpx = testCompletedRoute.getRouteGpx();
        Assertions.assertEquals(gpx.getClass(), GPX.class);
    }
}
