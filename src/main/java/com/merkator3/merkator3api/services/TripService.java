package com.merkator3.merkator3api.services;

import com.merkator3.merkator3api.models.Route;
import com.merkator3.merkator3api.models.Trip;
import io.jenetics.jpx.GPX;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.util.List;

public interface TripService {

    Trip addTrip(ObjectId userID, String tripName);

    Trip getTrip(ObjectId id);

    Trip addRouteToTrip(ObjectId tripID, ObjectId routeID);

    List<Trip> getUserTrips(ObjectId id);
}
