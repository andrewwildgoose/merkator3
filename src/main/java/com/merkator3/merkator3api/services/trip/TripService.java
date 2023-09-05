package com.merkator3.merkator3api.services.trip;

import com.merkator3.merkator3api.MapTools.MapBuilder;
import com.merkator3.merkator3api.models.route.planned.Route;
import com.merkator3.merkator3api.models.route.planned.RouteMapping;
import com.merkator3.merkator3api.models.trip.completed.CompletedTrip;
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

    <T extends TripMarker> List<List<Integer>> getTripRouteColours(T trip);

    <T extends TripMarker> List<String> getTripRouteIds(T trip);

    <T extends TripMarker> List<Route> getTripRoutes(T trip);

    <T extends TripMarker> List<String> getTripRouteNames(T trip);

    <T extends TripMarker> void setTripStaticMapUrl(T trip, String mapBoxKey);

    String getTripStaticMapUrl(Trip trip);
}
