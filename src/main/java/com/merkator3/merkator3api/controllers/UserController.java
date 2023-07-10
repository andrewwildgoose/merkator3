package com.merkator3.merkator3api.controllers;

import com.merkator3.merkator3api.GpxTools.GpxBuilder;
import com.merkator3.merkator3api.models.MerkatorUser;
import com.merkator3.merkator3api.models.Route;
import com.merkator3.merkator3api.services.RouteService;
import com.merkator3.merkator3api.services.RouteServiceImpl;
import com.merkator3.merkator3api.services.UserService;

import io.jenetics.jpx.GPX;
import org.bson.types.ObjectId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.io.IOException;

@RestController
@RequestMapping("/merkator/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    RouteService routeService;

    @GetMapping("/")
    public String getUserHomeMessage() {
        return "Welcome to merkator";
    }

    @PostMapping("/create")
    public MerkatorUser createUser(@RequestBody String username) {
        return userService.createUser(username);
    }

    @GetMapping("/{userID}")
    public MerkatorUser getUser(@PathVariable("userID") ObjectId id) {
        return userService.getUser(id);
    }

    @PostMapping("/{userID}/newroute")
    public String addRoute(@PathVariable("userID") ObjectId userID, @RequestParam("file") MultipartFile file,
                            @RequestParam("fileName") String fileName)
            throws IOException {
        // save the route to the repo
        try {
            GPX fileGPX = GpxBuilder.convertMultipartFileToGPX(file);

            ObjectId routeID = routeService.addRoute(userID, fileName, fileGPX);
            return "redirect:/merkator/user/{userID}/route/" + routeID;
        } catch (IOException e) {
            return e.toString();
        }
    }

    @PostMapping("/{userID}/newroutename")
    public String addRoute(@PathVariable("userID") ObjectId userID, @RequestBody String routeName) {
        // save the route to the repo
        ObjectId routeID = routeService.addRoute(userID, routeName);

        return "redirect:/merkator/user/{userID}/route/" + routeID;
    }

    @GetMapping("/{userID}/route/{routeID}")
    public String getRoute(@PathVariable("userID") ObjectId userID, @PathVariable("routeID") ObjectId routeID) {
        Route route = routeService.getRoute(routeID);
        return route.getRouteName() + route.getRouteDescription();
    }
}
