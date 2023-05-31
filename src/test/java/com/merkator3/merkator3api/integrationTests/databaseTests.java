package com.merkator3.merkator3api.integrationTests;

import com.merkator3.merkator3api.models.User;
import com.merkator3.merkator3api.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class databaseTests {

    @Autowired
    private UserRepository userRepository;
    private User testUser;
    @BeforeEach
    void createUser() {
        testUser = new User("testUser");
    }

    @AfterEach
    void removeUser() {
        if (userRepository.findByUserName("testUser")!=null){
            userRepository.delete(testUser);
        }
    }

    @Test
    void userInserted() {
        userRepository.save(testUser);
        Assertions.assertEquals(userRepository.findByUserName("testUser").getUserName(),"testUser");
    }


    }
