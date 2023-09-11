package com.merkator3.merkator3api.unitTests;

import com.merkator3.merkator3api.models.route.planned.Route;
import com.merkator3.merkator3api.models.trip.planned.Trip;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TripTests {

    Trip testPlannedTrip1;

    @BeforeEach
    void setUp() {
        testPlannedTrip1 = new Trip("testTrip1");
    }
    @Test
    void testGetTripName() {
        String expectedName = "testTrip1";
        String tripName = testPlannedTrip1.getTripName();
        Assertions.assertEquals(tripName, expectedName);
    }

    @Test
    void testSetTripName() {
        String expectedName = "testTrip2";
        testPlannedTrip1.setTripName("testTrip2");
        String tripName = testPlannedTrip1.getTripName();
        Assertions.assertEquals(tripName, expectedName);
    }
}
