package com.merkator3.merkator3api.services.user;

import com.merkator3.merkator3api.models.user.MerkatorUser;
import com.merkator3.merkator3api.repositories.UserRepository;
import io.jsonwebtoken.lang.Assert;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

/**
 * Service providing for User related requests in the UserController
 */

@Service
@DependsOn("userRepository")
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        Assert.notNull(userRepository, "userRepository must not be null");
        this.userRepository = userRepository;
    }

    // Create user
    @Override
    public MerkatorUser createUser(String username, String password) {
        MerkatorUser merkatorUser = new MerkatorUser(username, password);
        return userRepository.save(merkatorUser);
    }

    // Delete user
    @Override
    public void deleteUser(ObjectId userId) {
        MerkatorUser user = userRepository.findById(userId);
        userRepository.delete(user);
    }

    @Override
    public MerkatorUser getUser(ObjectId userId) {
        return userRepository.findById(userId);
    }

    @Override
    public MerkatorUser findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}



