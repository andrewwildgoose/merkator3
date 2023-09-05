package com.merkator3.merkator3api.services.trip;

import com.merkator3.merkator3api.MapTools.MapBuilder;
import com.merkator3.merkator3api.StatTools.TripCalculator;
import com.merkator3.merkator3api.models.route.planned.Route;
import com.merkator3.merkator3api.models.route.planned.RouteMapping;
import com.merkator3.merkator3api.models.trip.planned.Trip;
import com.merkator3.merkator3api.models.trip.TripMarker;
import com.merkator3.merkator3api.models.trip.planned.TripResponse;
import com.merkator3.merkator3api.models.user.MerkatorUser;
import com.merkator3.merkator3api.repositories.RouteRepository;
import com.merkator3.merkator3api.repositories.TripRepository;
import com.merkator3.merkator3api.repositories.UserRepository;
import com.merkator3.merkator3api.services.route.RouteService;
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
    public TripServiceImpl(TripRepository tripRepository, RouteRepository routeRepository, UserRepository userRepository,
                           RouteService routeService) {
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
                setTripStaticMapUrl(trip, mapBoxKey);
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

    //Returns data relevant for populating a user's Trip Feed
    @Override
    @Transactional(readOnly = true)
    public TripResponse getTripResponse(ObjectId tripId) {

        Trip trip = tripRepository.findById(tripId);
        List<Route> tripRoutes = getTripRoutes(trip);

        if (trip.getTripRoutes() == null) { //return trip currently holding no routes
            return new TripResponse(tripId, tripId.toString(), trip.getTripName());
        } else { // populate the trip response with the corresponding route data
            Double tripLength = tripCalc.totalDistance(tripRoutes);
            Double tripElevationGain = tripCalc.totalElevationGain(tripRoutes);
            Double tripElevationLoss = tripCalc.totalElevationLoss(tripRoutes);
            // Build and return the trip response.
            return new TripResponse(
                    tripId,
                    tripId.toString(),
                    trip.getTripName(),
                    trip.getTripDescription(),
                    tripLength,
                    tripElevationGain,
                    tripElevationLoss,
                    getTripRouteNames(trip),
                    getTripGpxStrings(trip),
                    getTripRouteColours(trip),
                    getTripRouteIds(trip),
                    getTripStaticMapUrl(trip),
                    trip.getTripRoutes().size()
            );
        }
    }


    @Override
    public boolean tripBelongsToUser(ObjectId userID, ObjectId tripID) {
        MerkatorUser user = userRepository.findById(userID);
        return user.getUserTrips().contains(tripID);
    }

    @Override
    public boolean deleteTrip(ObjectId tripId) {
        try {
            Trip trip = tripRepository.findById(tripId);
            if (trip == null) {
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
        Trip trip = tripRepository.findById(tripId);
        return trip.getTripRoutes().stream()
                .map(route -> routeRepository.findById(route))
                .map(route -> new RouteMapping(route.getId().toString(), route.getRouteName()))
                .toList();
    }

    @Override
    public <T extends TripMarker> List<String> getTripGpxStrings(T trip) {
        return trip.getTripRoutes().stream()
                .map(route -> routeRepository.findById(route))
                .map(route -> {
                    try {
                        return routeService.getRouteGpxAsJSON(route.getId());
                    } catch (IOException | JSONException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public <T extends TripMarker> List<List<Integer>> getTripRouteColours(T trip) {
        return trip.getTripRoutes().stream()
                .map(route -> routeRepository.findById(route))
                .map(Route::getMapLineColor)
                .collect(Collectors.toList());
    }

    @Override
    public <T extends TripMarker> List<String> getTripRouteIds(T trip) {
        return trip.getTripRoutes().stream()
                .map(route -> routeRepository.findById(route))
                .map(Route::getId)
                .map(ObjectId::toString)
                .collect(Collectors.toList());
    }

    @Override
    public <T extends TripMarker> List<Route> getTripRoutes(T trip) {
        return trip.getTripRoutes().stream()
                .map(route -> routeRepository.findById(route))
                .collect(Collectors.toList());
    }

    @Override
    public <T extends TripMarker> List<String> getTripRouteNames(T trip) {
        return trip.getTripRoutes().stream()
                .map(route -> routeRepository.findById(route))
                .map(Route::getRouteName)
                .collect(Collectors.toList());
    }

    @Override
    public <T extends TripMarker> void setTripStaticMapUrl(T trip, String mapBoxKey) {
        MapBuilder mapBuilder = new MapBuilder(mapBoxKey);
        String mapUrl = mapBuilder.generateStaticMapImageUrl(getTripRoutes(trip));
        trip.setTripStaticMapUrl(mapUrl);
    }

    @Override
    public String getTripStaticMapUrl(Trip trip) {
        if (trip.getTripStaticMapUrl() == null) {
            setTripStaticMapUrl(trip, mapBoxKey);
        }
        return trip.getTripStaticMapUrl();
    }

}
