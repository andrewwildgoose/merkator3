package com.merkator3.merkator3api.controllers;

import com.merkator3.merkator3api.models.route.planned.AddRouteToTripRequest;
import com.merkator3.merkator3api.models.route.planned.RouteMapping;
import com.merkator3.merkator3api.models.trip.planned.Trip;
import com.merkator3.merkator3api.models.trip.planned.TripResponse;
import com.merkator3.merkator3api.models.user.MerkatorUser;
import com.merkator3.merkator3api.services.trip.TripService;
import com.merkator3.merkator3api.services.user.UserService;
import io.jsonwebtoken.lang.Assert;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/merkator/user")
public class TripController {

    private final UserService userService;
    private final TripService tripService;

    @Autowired
    public TripController(UserService userService, TripService tripService) {
        Assert.notNull(userService, "userService must not be null");
        Assert.notNull(tripService, "tripService must not be null");
        this.userService = userService;
        this.tripService = tripService;
    }

    // Return a list of the user's trips
    @GetMapping("/trips")
    public ResponseEntity<List<TripResponse>> getUserTrips(@AuthenticationPrincipal UserDetails userDetails) {
        MerkatorUser user = userService.findByEmail(userDetails.getUsername());
        List<Trip> trips = tripService.getUserTrips(user.getId());

        List<TripResponse> tripResponse = trips.stream()
                .map(trip -> tripService.getTripResponse(trip.getId()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(tripResponse);
    }

    @PostMapping("/new_trip")
    public ResponseEntity<String> addTrip(@AuthenticationPrincipal UserDetails userDetails,
                                          @RequestBody String tripName) {
        MerkatorUser user = userService.findByEmail(userDetails.getUsername());

        ObjectId tripID = tripService.addTrip(user.getId(), tripName);

        URI tripLocation = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("trip/{id}")
                .buildAndExpand(tripID)
                .toUri();

        return ResponseEntity.created(tripLocation).body("Trip added successfully.");
    }

    @GetMapping("/trip/{id}")
    public ResponseEntity<TripResponse> getTrip(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("id") ObjectId tripId) {
        TripResponse tripResponse = tripService.getTripResponse(tripId);
        return ResponseEntity.ok(tripResponse);
    }

    @PostMapping("/trip/add_route")
    public ResponseEntity<String> addRouteToTrip(@AuthenticationPrincipal UserDetails userDetails, @RequestBody AddRouteToTripRequest request) {
        MerkatorUser user = userService.findByEmail(userDetails.getUsername());

        ObjectId tripId = request.getTripId();
        if (!tripService.tripBelongsToUser(user.getId(), tripId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Trip does not belong to the user.");
        }
        ObjectId routeId = request.getRouteId();

        boolean success = tripService.addRouteToTrip(tripId, routeId);
        if (success) {
            return ResponseEntity.ok("Route added to trip.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding route to trip.");
        }
    }

    @DeleteMapping("/trip/{tripId}")
    public ResponseEntity<String> deleteTrip(@AuthenticationPrincipal UserDetails userDetails,
                                             @PathVariable("tripId") ObjectId tripId) {
        System.out.println("deleting trip");
        MerkatorUser user = userService.findByEmail(userDetails.getUsername());

        if (!tripService.tripBelongsToUser(user.getId(), tripId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Trip does not belong to the user.");
        }

        boolean success = tripService.deleteTrip(user, tripId);
        if (success) {
            System.out.println("trip deleted");
            return ResponseEntity.ok("Trip deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting trip.");
        }
    }


    @GetMapping("/trip/route_mappings")
    public ResponseEntity<List<RouteMapping>> tripRouteInfo(@AuthenticationPrincipal UserDetails userDetails,
                                                            @RequestParam("tripId") String tripIdString) {
        MerkatorUser user = userService.findByEmail(userDetails.getUsername());
        ObjectId tripId = new ObjectId(tripIdString);
        if (!tripService.tripBelongsToUser(user.getId(), tripId)) {
            RouteMapping error = new RouteMapping("Trip does not belong to the user.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.singletonList(error));
        }
        List<RouteMapping> routeMapping = tripService.getRouteMapping(tripId);

        return ResponseEntity.ok(routeMapping);
    }
}
