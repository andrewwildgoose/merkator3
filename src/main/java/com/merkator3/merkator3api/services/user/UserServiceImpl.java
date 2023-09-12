package com.merkator3.merkator3api.services.user;

import com.merkator3.merkator3api.models.user.MerkatorUser;
import com.merkator3.merkator3api.repositories.UserRepository;
import com.merkator3.merkator3api.services.route.RouteService;
import com.merkator3.merkator3api.services.trip.CompletedTripService;
import com.merkator3.merkator3api.services.trip.TripService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;


@Service
@DependsOn("userRepository")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RouteService routeService;

    @Autowired
    private TripService tripService;

    @Autowired
    private CompletedTripService completedTripService;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    // Create user
    @Override
    public MerkatorUser createUser(String username, String password) {
        MerkatorUser merkatorUser = new MerkatorUser(username, password);
        return userRepository.save(merkatorUser);
    }

    // Delete user
    @Override
    public void deleteUser(ObjectId userId) {
        MerkatorUser user = userRepository.findById(userId);
        userRepository.delete(user);
    }

    @Override
    public MerkatorUser getUser(ObjectId userId) {
        return userRepository.findById(userId);
    }

    @Override
    public MerkatorUser findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean deleteRoute(ObjectId userId, ObjectId routeId) {
        MerkatorUser user = userRepository.findById(userId);
        if (user != null && user.getUserRoutes().contains(routeId)) {
            // Delete the route from the route repository
            boolean success = routeService.deleteRoute(routeId);

            if (success) {
                // Remove routeId from the user's userRoutes list
                user.getUserRoutes().remove(routeId);
                userRepository.save(user);
                return true; // Route deleted successfully
            }
        }
        return false; // Either user not found or route deletion failed
    }

    @Override
    public boolean deleteTrip(ObjectId userId, ObjectId tripId) {
        MerkatorUser user = userRepository.findById(userId);
        if (user != null && user.getUserTrips().contains(tripId)) {
            // Delete the route from the route repository
            boolean success = tripService.deleteTrip(tripId);

            if (success) {
                // Remove routeId from the user's userRoutes list
                user.getUserTrips().remove(tripId);
                userRepository.save(user);
                return true; // Route deleted successfully
            }
        }
        return false; // Either user not found or route deletion failed
    }

    @Override
    public boolean deleteCompletedTrip(ObjectId userId, ObjectId completedTripId) {
        MerkatorUser user = userRepository.findById(userId);
        if (user != null && user.getUserCompletedTrips().contains(completedTripId)) {
            // Delete the route from the route repository
            boolean success = completedTripService.deleteTrip(completedTripId);

            if (success) {
                // Remove routeId from the user's userRoutes list
                user.getUserCompletedTrips().remove(completedTripId);
                userRepository.save(user);
                return true; // Route deleted successfully
            }
        }
        return false; // Either user not found or route deletion failed
    }
}



