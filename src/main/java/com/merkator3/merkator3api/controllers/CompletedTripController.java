package com.merkator3.merkator3api.controllers;

import com.merkator3.merkator3api.models.MerkatorUser;
import com.merkator3.merkator3api.services.RouteService;
import com.merkator3.merkator3api.services.TripService;
import com.merkator3.merkator3api.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/merkator/user/")
public class CompletedTripController {
    @Autowired
    private UserService userService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private TripService tripService;
    @PostMapping("/complete_trip")
    public void completeTrip(@AuthenticationPrincipal UserDetails userDetails,
                             @RequestParam("file") List<MultipartFile> file,
                             @RequestParam("routeId") List<String> routeId,
                             @RequestParam("tripId") String tripIdString){
        MerkatorUser user = userService.findByEmail(userDetails.getUsername());
        ObjectId tripId = new ObjectId(tripIdString);
        if (tripService.tripBelongsToUser(user.getId(), tripId)){
            System.out.println(tripId);
            System.out.println(routeId);
            System.out.println(file);
        }
        else {
            System.out.println("Route does not belong to user");
        }
    }
}
