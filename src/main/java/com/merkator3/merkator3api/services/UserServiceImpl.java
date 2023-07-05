package com.merkator3.merkator3api.services;

import com.merkator3.merkator3api.models.MerkatorUser;
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
    public MerkatorUser createUser(String userName) {
        MerkatorUser merkatorUser = new MerkatorUser(userName);
        return userRepository.save(merkatorUser);
    }

    // READ
    @Override
    public MerkatorUser getUser(ObjectId userId) {
        return userRepository.findById(userId);
    }


}
