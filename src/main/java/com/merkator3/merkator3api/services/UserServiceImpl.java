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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
@DependsOn("userRepository")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final RouteRepository routeRepository;

    @Autowired
    private TripRepository tripRepository;

    public UserServiceImpl(UserRepository userRepository, RouteRepository routeRepository) {
        this.userRepository = userRepository;
        this.routeRepository = routeRepository;
    }

    // CREATE
    @Override
    public MerkatorUser createUser(String username, String password) {
        MerkatorUser merkatorUser = new MerkatorUser(username, password);
        return userRepository.save(merkatorUser);
    }

    // READ
    @Override
    public MerkatorUser getUser(ObjectId userId) {
        return userRepository.findById(userId);
    }

    @Override
    public List<Route> getUserRoutes(ObjectId userId) {
        MerkatorUser user = userRepository.findById(userId);
        List<ObjectId> userRouteIds = user.getUserRoutes();
        List<String> routeIdsString = userRouteIds.stream()
                .map(ObjectId::toString)
                .collect(Collectors.toList());
        return routeRepository.findAllById(routeIdsString);
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
    public MerkatorUser findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


}
