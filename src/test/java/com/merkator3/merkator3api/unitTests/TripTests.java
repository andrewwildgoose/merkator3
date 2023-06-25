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
public class TripTests {

    Route testRoute1;
    Route testRoute2;
    Route testRoute3;

    @BeforeEach
    void createTrip() throws IOException {
        testRoute1 = new Route("testRoute1");
        Path path1 = Path.of("src/test/TestFiles/GPX/Belgium to Netherlands/2021-10-16_525667120_Brussles to Bruges (Lunch in Ghent).gpx")
        testRoute1.setRouteGpx(GPX.read(path1));

        testRoute2 = new Route("testRoute2");
        testRoute3 = new Route("testRoute3");

    }
}
