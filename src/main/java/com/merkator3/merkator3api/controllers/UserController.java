package com.merkator3.merkator3api.controllers;

import com.merkator3.merkator3api.models.user.MerkatorUser;
import com.merkator3.merkator3api.services.user.UserService;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * Controller handling all API requests for user details
 */

@RestController
@RequestMapping("/merkator/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        Assert.notNull(userService,"userService must not be null");
        this.userService = userService;
    }

    // Return User Details
    @GetMapping("/details")
    public ResponseEntity<MerkatorUser> getUserDetails(@AuthenticationPrincipal UserDetails userDetails) {
        MerkatorUser user = userService.findByEmail(userDetails.getUsername());
        return ResponseEntity.ok(user);
    }


}
