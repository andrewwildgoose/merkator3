package com.merkator3.merkator3api.controllers;

import com.merkator3.merkator3api.GpxTools.GpxBuilder;
import com.merkator3.merkator3api.models.MerkatorUser;
import com.merkator3.merkator3api.models.Route;
import com.merkator3.merkator3api.models.Trip;
import com.merkator3.merkator3api.services.RouteService;
import com.merkator3.merkator3api.services.RouteServiceImpl;
import com.merkator3.merkator3api.services.TripService;
import com.merkator3.merkator3api.services.UserService;

import io.jenetics.jpx.GPX;
import org.bson.types.ObjectId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/merkator/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    RouteService routeService;
    @Autowired
    TripService tripService;

    //user welcome page
    @GetMapping("/")
    public String getUserHomeMessage() {
        return "Welcome to merkator";
    }

    // ADDING & RETRIEVING USERS

    // create a new user
    @PostMapping("/create")
    public MerkatorUser createUser(@RequestBody String username) {
        return userService.createUser(username);
    }

    // return a user by ID
    @GetMapping("/{userID}")
    public MerkatorUser getUser(@PathVariable("userID") ObjectId id) {
        return userService.getUser(id);
    }


    // ADDING & RETRIEVING ROUTES

    // add a new route with a gpx file
    @PostMapping("/{userID}/newroute")
    public String addRoute(@PathVariable("userID") ObjectId userID, @RequestParam("file") MultipartFile file,
                            @RequestParam("fileName") String fileName)
            throws IOException {
        // save the route to the repo
        try {
            GPX fileGPX = GpxBuilder.convertMultipartFileToGPX(file);

            ObjectId routeID = routeService.addRoute(userID, fileName, fileGPX);
            return "redirect:/merkator/user/" + userID + "/route/" + routeID;
        } catch (IOException e) {
            return e.toString();
        }
    }

    // add a new route with no file
    @PostMapping("/{userID}/newroutename")
    public String addRoute(@PathVariable("userID") ObjectId userID, @RequestBody String routeName) {
        // save the route to the repo
        ObjectId routeID = routeService.addRoute(userID, routeName);

        return "redirect:/merkator/user/" + userID + "/route/" + routeID;
    }

    // get a route by ID
    @GetMapping("/{userID}/route/{routeID}")
    public String getRoute(@PathVariable("userID") ObjectId userID, @PathVariable("routeID") ObjectId routeID) {
        Route route = routeService.getRoute(routeID);
        return route.getRouteName() + "\n" + route.getRouteDescription();
    }

    // get a list of the user's routes
    @GetMapping("/{userID}/routes")
    public List<Route> getUserRoutes(@PathVariable("userID") ObjectId userID) {
        return userService.getUserRoutes(userID);
    }

    // ADDING & RETRIEVING TRIPS

    // create a new trip
    @PostMapping("/{userID}/newtrip")
    public Trip addTrip(@PathVariable("userID") ObjectId userID, @RequestBody String tripName) {
        return tripService.addTrip(userID, tripName);
    }

    // get a trip by ID
    @GetMapping("/{userID}/trip/{tripID}")
    public Trip getTrip(@PathVariable("userID") ObjectId userID, @PathVariable("tripID") ObjectId tripID) {
        return tripService.getTrip(tripID);
    }

    // add a route to a trip
    @PostMapping("{userID}/trip/{tripID}/addroute")
    public Trip addRouteToTrip(@PathVariable("userID") ObjectId userID, @PathVariable("tripID") ObjectId tripID,
                                @RequestBody String routeID) {
        ObjectId objRouteID = new ObjectId(routeID);
        return tripService.addRouteToTrip(tripID, objRouteID);
    }
}
