package com.merkator3.merkator3api.controllers;

import com.merkator3.merkator3api.models.MerkatorUser;
import com.merkator3.merkator3api.models.Route;
import com.merkator3.merkator3api.models.Trip;
import com.merkator3.merkator3api.services.RouteService;
import com.merkator3.merkator3api.services.TripService;
import com.merkator3.merkator3api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/merkator/user")
public class UserController2 {

    @Autowired
    private UserService userService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private TripService tripService;

    @GetMapping("/details")
    public ResponseEntity<MerkatorUser> getUserDetails(@AuthenticationPrincipal UserDetails userDetails) {
        MerkatorUser user = userService.findByEmail(userDetails.getUsername());
        return ResponseEntity.ok(user);
    }

    @GetMapping("/routes")
    public ResponseEntity<List<Route>> getUserRoutes(@AuthenticationPrincipal UserDetails userDetails) {
        MerkatorUser user = userService.findByEmail(userDetails.getUsername());
        List<Route> routes = routeService.getUserRoutes(user.getId());
        return ResponseEntity.ok(routes);
    }

    @GetMapping("/trips")
    public ResponseEntity<List<Trip>> getUserTrips(@AuthenticationPrincipal UserDetails userDetails) {
        MerkatorUser user = userService.findByEmail(userDetails.getUsername());
        List<Trip> trips = tripService.getUserTrips(user.getId());
        return ResponseEntity.ok(trips);
    }
}
