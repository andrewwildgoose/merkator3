package com.merkator3.merkator3api.controllers;

import com.merkator3.merkator3api.models.User;
import com.merkator3.merkator3api.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/merkator/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String getUserHomeMessage() {
        return "Welcome to merkator";
    }

    @PostMapping("/create")
    public User createUser(@RequestBody String username) {
        return userService.createUser(username);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") ObjectId id) {
        return userService.getUser(id);
    }
}
