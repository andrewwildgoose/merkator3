package com.merkator3.merkator3api.services;

import com.merkator3.merkator3api.StatTools.TripCalculator;
import com.merkator3.merkator3api.models.*;
import com.merkator3.merkator3api.repositories.RouteRepository;
import com.merkator3.merkator3api.repositories.TripRepository;
import com.merkator3.merkator3api.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${merkator.api.mapBoxKey}")
    private String mapBoxKey;

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
        PlannedTrip plannedTrip = new PlannedTrip(tripName);
        tripRepository.save(plannedTrip);

        // save the trip to the user's trips
        MerkatorUser user = userRepository.findById(userID);
        user.addTrip(plannedTrip.getId());
        userRepository.save(user);
        return plannedTrip.getId();
    }

    @Override
    public PlannedTrip getTrip(ObjectId id) {
        return tripRepository.findById(id);
    }

    @Override
    public boolean addRouteToTrip(ObjectId tripID, ObjectId routeID) {
            PlannedTrip plannedTrip = tripRepository.findById(tripID);
            Route route = routeRepository.findById(routeID);

            if (plannedTrip != null && route != null) {
                plannedTrip.addRoute(route);
                plannedTrip.setTripStaticMapUrl(mapBoxKey);
                tripRepository.save(plannedTrip);
                return true;
            }
            return false;
    }

    @Override
    public List<PlannedTrip> getUserTrips(ObjectId userId) {
        MerkatorUser user = userRepository.findById(userId);
        List<ObjectId> userTripIds = user.getUserTrips();
        List<String> tripIdsString = userTripIds.stream()
                .map(ObjectId::toString)
                .collect(Collectors.toList());
        return tripRepository.findAllById(tripIdsString);
    }

    //Returns data relevant for populating a user's Trip Feed
    @Override
    @Transactional(readOnly = true)
    public TripResponse getTripResponse(ObjectId tripId) {

        PlannedTrip plannedTrip = tripRepository.findById(tripId);

        if (plannedTrip.getTripRoutes() == null) { //return trip currently holding no routes
            return new TripResponse(tripId, tripId.toString(), plannedTrip.getTripName());
        } else { // populate the trip response with the corresponding route data
            Double tripLength = tripCalc.totalDistance(plannedTrip);
            Double tripElevationGain = tripCalc.totalElevationGain(plannedTrip);
            Double tripElevationLoss = tripCalc.totalElevationLoss(plannedTrip);
            List<String> tripRouteNames = plannedTrip.getTripRoutes().stream()
                    .map(Route::getRouteName)
                    .collect(Collectors.toList());
            List<String> tripGpxStrings = plannedTrip.getTripRoutes().stream()
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
                    plannedTrip.getTripName(),
                    plannedTrip.getTripDescription(),
                    tripLength,
                    tripElevationGain,
                    tripElevationLoss,
                    tripRouteNames,
                    tripGpxStrings,
                    plannedTrip.getTripStaticMapUrl(mapBoxKey),
                    plannedTrip.getTripRoutes().size()
            );
        }
    }

    @Override
    public boolean tripBelongsToUser(ObjectId userID, ObjectId tripID) {
        PlannedTrip plannedTrip = tripRepository.findById(tripID);
        MerkatorUser user = userRepository.findById(userID);
        return user.getUserTrips().contains(tripID);
    }

    @Override
    public boolean deleteTrip(ObjectId tripId) {
        try {
            PlannedTrip plannedTrip = tripRepository.findById(tripId);
            if (plannedTrip == null) {
                return false; // Route not found
            }
            // Remove route from route repository
            tripRepository.deleteById(String.valueOf(tripId));
            return true; // Route deleted successfully
        } catch (Exception e) {
            return false;
        }
    }

    // Returns a list of the Trip's Route names & ids
    @Override
    public List<RouteMapping> getRouteMapping(ObjectId tripId) {
        PlannedTrip plannedTrip = tripRepository.findById(tripId);
        return plannedTrip.getTripRoutes().stream()
                .map(route -> new RouteMapping(route.getId().toString(), route.getRouteName()))
                .toList();
    }
}
