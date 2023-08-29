package com.merkator3.merkator3api.controllers;

import com.merkator3.merkator3api.models.MerkatorUser;
import com.merkator3.merkator3api.models.RouteMapping;
import com.merkator3.merkator3api.models.TripResponse;
import com.merkator3.merkator3api.services.RouteService;
import com.merkator3.merkator3api.services.TripService;
import com.merkator3.merkator3api.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/merkator/user")
public class TripController {

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
//    public ResponseEntity<CompletedTrip> completeTrip(@AuthenticationPrincipal UserDetails userDetails,
//                                                        @RequestParam("files") List<MultipartFile> files,
//                                                        @RequestParam("routeIds") List<String> routeIds,
//                                                        @RequestParam("tripId") String tripId) {
//
//    }

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
