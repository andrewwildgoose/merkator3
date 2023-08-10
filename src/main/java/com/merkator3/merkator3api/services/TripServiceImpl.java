package com.merkator3.merkator3api.services;

import com.merkator3.merkator3api.models.MerkatorUser;
import com.merkator3.merkator3api.models.Route;
import com.merkator3.merkator3api.models.Trip;
import com.merkator3.merkator3api.repositories.RouteRepository;
import com.merkator3.merkator3api.repositories.TripRepository;
import com.merkator3.merkator3api.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public TripServiceImpl(TripRepository tripRepository, RouteRepository routeRepository, UserRepository userRepository) {
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
        this.routeRepository = routeRepository;
    }
    @Override
    public Trip addTrip(ObjectId userID, String tripName) {
        // create and save the trip to the repo
        Trip trip = new Trip(tripName);
        tripRepository.save(trip);

        // save the trip to the user's trips
        MerkatorUser user = userRepository.findById(userID);
        user.addTrip(trip.getId());
        userRepository.save(user);
        return tripRepository.findById(trip.getId());
    }

    @Override
    public Trip getTrip(ObjectId id) {
        return tripRepository.findById(id);
    }

    @Override
    public Trip addRouteToTrip(ObjectId tripID, ObjectId routeID) {
        Route route = routeRepository.findById(routeID);
        Trip trip = tripRepository.findById(tripID);
        trip.addRoute(route);
        tripRepository.save(trip);
        return trip;
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
}
