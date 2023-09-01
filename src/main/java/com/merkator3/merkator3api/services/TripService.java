package com.merkator3.merkator3api.services;

import com.merkator3.merkator3api.models.route.planned.RouteMapping;
import com.merkator3.merkator3api.models.trip.planned.Trip;
import com.merkator3.merkator3api.models.trip.TripMarker;
import com.merkator3.merkator3api.models.trip.planned.TripResponse;
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

    <T extends TripMarker> List<String> getTripGpxStrings(T trip);
}
