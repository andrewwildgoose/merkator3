package com.merkator3.merkator3api.controllers;

import com.merkator3.merkator3api.GpxTools.GpxBuilder;
import com.merkator3.merkator3api.models.MerkatorUser;
import com.merkator3.merkator3api.models.Route;
import com.merkator3.merkator3api.models.RouteResponse;
import com.merkator3.merkator3api.models.Trip;
import com.merkator3.merkator3api.services.RouteService;
import com.merkator3.merkator3api.services.TripService;
import com.merkator3.merkator3api.services.UserService;
import io.jenetics.jpx.GPX;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
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

    // Return User Details
    @GetMapping("/details")
    public ResponseEntity<MerkatorUser> getUserDetails(@AuthenticationPrincipal UserDetails userDetails) {
        MerkatorUser user = userService.findByEmail(userDetails.getUsername());
        return ResponseEntity.ok(user);
    }

    // Return a list of the user's routes
    @GetMapping("/routes")
    public ResponseEntity<List<Route>> getUserRoutes(@AuthenticationPrincipal UserDetails userDetails) {
        MerkatorUser user = userService.findByEmail(userDetails.getUsername());
        System.out.println("Getting User Routes");
        List<Route> routes = routeService.getUserRoutes(user.getId());
        routes.forEach(route -> System.out.println("User Route: " + route));
        return ResponseEntity.ok(routes);
    }

    // Return a list of the user's trips
    @GetMapping("/trips")
    public ResponseEntity<List<Trip>> getUserTrips(@AuthenticationPrincipal UserDetails userDetails) {
        MerkatorUser user = userService.findByEmail(userDetails.getUsername());
        List<Trip> trips = tripService.getUserTrips(user.getId());
        return ResponseEntity.ok(trips);
    }

    // Add a new route to the user's routes
    @PostMapping("/newroute")
    public ResponseEntity<String> addRoute(@AuthenticationPrincipal UserDetails userDetails,
                                            @RequestParam("file") MultipartFile file,
                                            @RequestParam("fileName") String fileName) {
        try {
            MerkatorUser user = userService.findByEmail(userDetails.getUsername());

            GPX fileGPX = GpxBuilder.convertMultipartFileToGPX(file);

            ObjectId routeID = routeService.addRoute(user.getId(), fileName, fileGPX);

            URI routeLocation = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("route/{id}")
                    .buildAndExpand(routeID)
                    .toUri();

            return ResponseEntity.created(routeLocation).body("Route added successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding route: " + e);
        }
    }


    @GetMapping("/route/{id}")
    public ResponseEntity<RouteResponse> getRouteById(@PathVariable("id") ObjectId routeID,
                                                        @AuthenticationPrincipal UserDetails userDetails) throws IOException {
        MerkatorUser user = userService.findByEmail(userDetails.getUsername());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        Route route = routeService.getRoute(routeID);
        if (route == null) {
            return ResponseEntity.notFound().build();
        }

        RouteResponse routeResponse = new RouteResponse(route.getRouteName(), route.getRouteDescription(), route.getRouteGpxString());
        return ResponseEntity.ok(routeResponse);
    }
}
