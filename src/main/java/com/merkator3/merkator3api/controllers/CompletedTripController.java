package com.merkator3.merkator3api.controllers;

import com.merkator3.merkator3api.models.trip.completed.CompletedTripResponse;
import com.merkator3.merkator3api.models.user.MerkatorUser;
import com.merkator3.merkator3api.services.trip.CompletedTripService;
import com.merkator3.merkator3api.services.route.RouteService;
import com.merkator3.merkator3api.services.trip.TripService;
import com.merkator3.merkator3api.services.user.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Autowired
    private CompletedTripService completedTripService;

    @PostMapping("/complete_trip")
    public ResponseEntity<String> completeTrip(@AuthenticationPrincipal UserDetails userDetails,
                                                              @RequestParam("file") List<MultipartFile> file,
                                                              @RequestParam("routeId") List<String> routeId,
                                                              @RequestParam("tripId") String tripIdString) throws IOException {
        MerkatorUser user = userService.findByEmail(userDetails.getUsername());
        ObjectId userId = user.getId();
        ObjectId tripId = new ObjectId(tripIdString);

        if (!tripService.tripBelongsToUser(userId, tripId)){
            System.out.println("Trip does not belong to user");
            String error = "Trip does not belong to user";
            ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);

        }
        String completedTripId = completedTripService.handleTripCompletion(userId, tripId, routeId, file);

        return ResponseEntity.ok(completedTripId);
    }

    @GetMapping("/completed_trip/{id}")
    public ResponseEntity<CompletedTripResponse> getCompleteTrip(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("id") ObjectId completedTripId) {

        CompletedTripResponse completedTripResponse = completedTripService.getCompletedTrip(completedTripId);

        return ResponseEntity.ok(completedTripResponse);
    }
}