package com.merkator3.merkator3api.integrationTests;

import com.merkator3.merkator3api.models.user.MerkatorUser;
import com.merkator3.merkator3api.models.route.planned.Route;
import com.merkator3.merkator3api.repositories.RouteRepository;
import com.merkator3.merkator3api.repositories.UserRepository;
import io.jenetics.jpx.GPX;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Path;

@SpringBootTest
public class DatabaseTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RouteRepository routeRepository;
    private MerkatorUser testMerkatorUser;
    @BeforeEach
    void createUser() {
        if (userRepository.findByEmail("testUser@google.com") == null) {
            testMerkatorUser = new MerkatorUser("testUser@google.com", "testUser");
        }
    }

    @AfterEach
    void removeUser() {
        if (userRepository.findByEmail("testUser@google.com") == null){
            userRepository.delete(testMerkatorUser);
        }
    }

    @Test
    void testUserInserted() {
        userRepository.save(testMerkatorUser);
        Assertions.assertEquals(userRepository.findByEmail("testUser@google.com").getName(),"testUser");
    }

    @Test
    void testRouteInserted() {
        Route DBTestRoute = new Route("DBTestRoute");
        routeRepository.save(DBTestRoute);
        Assertions.assertEquals(DBTestRoute.getRouteName(), routeRepository.findByRouteName("DBTestRoute").getRouteName());
        routeRepository.deleteById(DBTestRoute.getId().toString());
    }


    @Test
    void testRouteInsertedWithGPX() throws IOException {
        // Create a test route
        Route DBTestRouteGPX = new Route("DBTestRouteGPX");

        // Read the GPX file
        Path path = Path.of("src/test/TestFiles/GPX/London to Brighton Return.gpx");
        GPX routeGPX = GPX.read(path);

        // Set the GPX on the test route
        DBTestRouteGPX.setRouteGpxString(routeGPX);

        // Save the test route to the repository
        routeRepository.save(DBTestRouteGPX);

        // Retrieve the saved route from the repository
        Route retrievedRoute = routeRepository.findByRouteName("DBTestRouteGPX");

        retrievedRoute.getRouteGpx().getTracks().forEach(track -> {
                    track.getSegments().forEach(segment -> {
                        segment.getPoints().forEach(System.out::println);
                    });
                });

        // Assert that the saved route and retrieved route have similar properties
        Assertions.assertEquals(DBTestRouteGPX.getRouteGpx().getTracks(), retrievedRoute.getRouteGpx().getTracks());

        // Cleanup: Delete the saved route from the repository
        routeRepository.deleteById(DBTestRouteGPX.getId().toString());
        }
}
