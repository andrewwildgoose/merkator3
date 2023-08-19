package com.merkator3.merkator3api.services;

import com.merkator3.merkator3api.StatTools.TripCalculator;
import com.merkator3.merkator3api.models.*;
import com.merkator3.merkator3api.repositories.RouteRepository;
import com.merkator3.merkator3api.repositories.TripRepository;
import com.merkator3.merkator3api.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@DependsOn({"tripRepository", "routeRepository", "userRepository"})
public class TripServiceImpl implements TripService {

    @Autowired
    private  TripRepository tripRepository;
    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RouteService routeService;

    private final TripCalculator tripCalc = new TripCalculator();

    public TripServiceImpl(TripRepository tripRepository, RouteRepository routeRepository, UserRepository userRepository, RouteService routeService) {
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
        this.routeRepository = routeRepository;
        this.routeService = routeService;
    }
    @Override
    public ObjectId addTrip(ObjectId userID, String tripName) {
        // create and save the trip to the repo
        Trip trip = new Trip(tripName);
        tripRepository.save(trip);

        // save the trip to the user's trips
        MerkatorUser user = userRepository.findById(userID);
        user.addTrip(trip.getId());
        userRepository.save(user);
        return trip.getId();
    }

    @Override
    public Trip getTrip(ObjectId id) {
        return tripRepository.findById(id);
    }

    @Override
    public boolean addRouteToTrip(ObjectId tripID, ObjectId routeID) {
            Trip trip = tripRepository.findById(tripID);
            Route route = routeRepository.findById(routeID);

            if (trip != null && route != null) {
                trip.addRoute(route);
                tripRepository.save(trip);
                return true;
            }
            return false;
    }

    @Override
    public List<Trip> getUserTrips(ObjectId userId) {
        MerkatorUser user = userRepository.findById(userId);
        List<ObjectId> userTripIds = user.getUserTrips();
        List<String> tripIdsString = userTripIds.stream()
                .map(ObjectId::toString)
                .collect(Collectors.toList());
        return tripRepository.findAllById(tripIdsString);
    }

    @Override
    @Transactional(readOnly = true)
    public TripResponse getTripResponse(ObjectId tripId) {

        Trip trip = tripRepository.findById(tripId);

        if (trip.getTripRoutes().isEmpty()) { //return trip currently holding no routes
            return new TripResponse(tripId, tripId.toString(), trip.getTripName());
        } else { // populate the trip response with the corresponding route data
            Double tripLength = tripCalc.totalDistance(trip);
            Double tripElevationGain = tripCalc.totalElevationGain(trip);
            Double tripElevationLoss = tripCalc.totalElevationLoss(trip);
            List<String> tripRouteNames = trip.getTripRoutes().stream()
                    .map(Route::getRouteName)
                    .collect(Collectors.toList());
            List<String> tripGpxStrings = trip.getTripRoutes().stream()
                    .map(route -> {
                        try {
                            return routeService.getRouteGpxAsJSON(route.getId());
                        } catch (IOException | JSONException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toList());
            return new TripResponse(
                    tripId,
                    tripId.toString(),
                    trip.getTripName(),
                    trip.getTripDescription(),
                    tripLength,
                    tripElevationGain,
                    tripElevationLoss,
                    tripRouteNames,
                    tripGpxStrings
            );
        }
    }

    @Override
    public boolean tripBelongsToUser(ObjectId userID, ObjectId tripID) {
        Trip trip = tripRepository.findById(tripID);
        MerkatorUser user = userRepository.findById(userID);
        return user.getUserTrips().contains(tripID);
    }
}
