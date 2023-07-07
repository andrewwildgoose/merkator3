package com.merkator3.merkator3api.integrationTests;

import com.merkator3.merkator3api.models.MerkatorUser;
import com.merkator3.merkator3api.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DatabaseTests {

    @Autowired
    private UserRepository userRepository;
    private MerkatorUser testMerkatorUser;
    @BeforeEach
    void createUser() {
        if (userRepository.findByUsername("testUser") == null) {
            testMerkatorUser = new MerkatorUser("testUser");
        }
    }

    @AfterEach
    void removeUser() {
        if (userRepository.findByUsername("testUser")!=null){
            userRepository.delete(testMerkatorUser);
        }
    }

    @Test
    void userInserted() {
        userRepository.save(testMerkatorUser);
        Assertions.assertEquals(userRepository.findByUsername("testUser").getUsername(),"testUser");
    }
}
