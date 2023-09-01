package com.merkator3.merkator3api.controllers;

import com.merkator3.merkator3api.models.MerkatorUser;
import com.merkator3.merkator3api.models.route.planned.RouteResponse;
import com.merkator3.merkator3api.services.RouteService;
import com.merkator3.merkator3api.services.UserService;
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
public class RouteController {

    @Autowired
    private UserService userService;

    @Autowired
    private RouteService routeService;

    // Return a list of the user's routes
    @GetMapping("/routes")
    public ResponseEntity<List<RouteResponse>> getUserRoutes(@AuthenticationPrincipal UserDetails userDetails) {
        MerkatorUser user = userService.findByEmail(userDetails.getUsername());
        List<RouteResponse> routeResponses = routeService.getRouteResponsesForUser(user.getId());
        return ResponseEntity.ok(routeResponses);
    }


    // Add a new route to the user's routes
    @PostMapping("/new_route")
    public ResponseEntity<String> addRoute(@AuthenticationPrincipal UserDetails userDetails,
                                           @RequestParam("file") MultipartFile file,
                                           @RequestParam("fileName") String fileName) {
        try {
            MerkatorUser user = userService.findByEmail(userDetails.getUsername());

            ObjectId routeID = routeService.addRoute(user.getId(), fileName, file);

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
    public ResponseEntity<RouteResponse> getRoute(@PathVariable("id") ObjectId routeId) throws IOException {
        RouteResponse routeResponse = routeService.getRouteResponse(routeId);
        return ResponseEntity.ok(routeResponse);
    }

    @DeleteMapping("/route/{routeId}")
    public ResponseEntity<String> deleteRoute(@AuthenticationPrincipal UserDetails userDetails,
                                              @PathVariable("routeId") ObjectId routeId) {
        MerkatorUser user = userService.findByEmail(userDetails.getUsername());

        if (!routeService.routeBelongsToUser(user.getId(), routeId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Route does not belong to the user.");
        }

        boolean success = userService.deleteRoute(user.getId(), routeId);
        if (success) {
            return ResponseEntity.ok("Route deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting route.");
        }
    }
}
