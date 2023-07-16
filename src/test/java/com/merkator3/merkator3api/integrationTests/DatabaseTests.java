package com.merkator3.merkator3api.integrationTests;

import com.merkator3.merkator3api.models.MerkatorUser;
import com.merkator3.merkator3api.models.Route;
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
import java.util.Optional;

@SpringBootTest
public class DatabaseTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RouteRepository routeRepository;
    private MerkatorUser testMerkatorUser;
    @BeforeEach
    void createUser() {
        if (userRepository.findByUsername("testUser") == null) {
            testMerkatorUser = new MerkatorUser("testUser");
        }
    }

    @AfterEach
    void removeUser() {
        if (userRepository.findByUsername("testUser")!=null){
            userRepository.delete(testMerkatorUser);
        }
    }

    @Test
    void userInserted() {
        userRepository.save(testMerkatorUser);
        Assertions.assertEquals(userRepository.findByUsername("testUser").getUsername(),"testUser");
    }

    @Test
    void routeInserted() {
        Route DBTestRoute = new Route("DBTestRoute");
        routeRepository.save(DBTestRoute);
        Assertions.assertEquals(DBTestRoute.getRouteName(), routeRepository.findByRouteName("DBTestRoute").getRouteName());
        routeRepository.deleteById(DBTestRoute.getId().toString());
    }


    @Test
    void routeInsertedWithGPX() throws IOException {
        // Create a test route
        Route DBTestRouteGPX = new Route("DBTestRouteGPX");

        // Read the GPX file
        Path path = Path.of("src/test/TestFiles/GPX/London to Brighton Return.gpx");
        GPX routeGPX = GPX.read(path);

        // Set the GPX on the test route
        DBTestRouteGPX.setRouteGpx(routeGPX);

        // Save the test route to the repository
        routeRepository.save(DBTestRouteGPX);

        Optional<Route> retrievedRouteByID = routeRepository.findById("64b3e18a63ecc24f72bb861c");
        // Retrieve the saved route from the repository
        Route retrievedRoute = routeRepository.findByRouteName("DBTestRouteGPX");

        // Assert that the saved route and retrieved route have similar properties
        Assertions.assertEquals(DBTestRouteGPX.getRouteGpx().getTracks(), retrievedRoute.getRouteGpx().getTracks());
        Assertions.assertEquals(DBTestRouteGPX.getRouteGpx().getWayPoints(), retrievedRoute.getRouteGpx().getWayPoints());

        // Cleanup: Delete the saved route from the repository
        routeRepository.deleteById(DBTestRouteGPX.getId().toString());
        }
}
