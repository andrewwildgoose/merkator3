package com.merkator3.merkator3api.services;

import com.merkator3.merkator3api.models.User;
import com.merkator3.merkator3api.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;


@Service
@DependsOn("userRepository")
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // CREATE
    @Override
    public User createUser(String userName) {
        User user = new User(userName);
        return userRepository.save(user);
    }

    // READ
    @Override
    public User getUser(ObjectId userId) {
        return userRepository.findById(userId);
    }


}
