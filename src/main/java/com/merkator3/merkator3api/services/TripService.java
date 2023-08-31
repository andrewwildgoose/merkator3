package com.merkator3.merkator3api.services;

import com.merkator3.merkator3api.models.RouteMapping;
import com.merkator3.merkator3api.models.Trip;
import com.merkator3.merkator3api.models.TripResponse;
import org.bson.types.ObjectId;
import java.util.List;

public interface TripService {

    ObjectId addTrip(ObjectId userID, String tripName);

    Trip getTrip(ObjectId id);

    boolean addRouteToTrip(ObjectId tripID, ObjectId routeID);

    List<Trip> getUserTrips(ObjectId id);

    TripResponse getTripResponse(ObjectId tripId);

    boolean tripBelongsToUser(ObjectId id, ObjectId tripID);

    boolean deleteTrip(ObjectId tripId);

    List<RouteMapping> getRouteMapping(ObjectId tripId);
}
