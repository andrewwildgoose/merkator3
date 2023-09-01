package com.merkator3.merkator3api.controllers;

import com.merkator3.merkator3api.models.trip.planned.CompletedTripResponse;
import com.merkator3.merkator3api.models.MerkatorUser;
import com.merkator3.merkator3api.services.CompletedTripService;
import com.merkator3.merkator3api.services.RouteService;
import com.merkator3.merkator3api.services.TripService;
import com.merkator3.merkator3api.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<CompletedTripResponse> completeTrip(@AuthenticationPrincipal UserDetails userDetails,
                                                              @RequestParam("file") List<MultipartFile> file,
                                                              @RequestParam("routeId") List<String> routeId,
                                                              @RequestParam("tripId") String tripIdString) throws IOException {
        MerkatorUser user = userService.findByEmail(userDetails.getUsername());
        ObjectId tripId = new ObjectId(tripIdString);
        if (!tripService.tripBelongsToUser(user.getId(), tripId)){
            System.out.println("Route does not belong to user");
            CompletedTripResponse error = completedTripService.tripCompletionError(tripId);
            ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);

        }
        CompletedTripResponse completedTripResponse = completedTripService.handleTripCompletion(tripId, routeId, file);
        System.out.println(tripId);
        System.out.println(routeId);
        System.out.println(file);
        return ResponseEntity.ok(completedTripResponse);
    }
}
