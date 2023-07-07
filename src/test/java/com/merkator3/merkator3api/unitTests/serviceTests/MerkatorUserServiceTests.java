package com.merkator3.merkator3api.unitTests.serviceTests;

import com.merkator3.merkator3api.models.MerkatorUser;
import com.merkator3.merkator3api.repositories.UserRepository;
import com.merkator3.merkator3api.services.UserService;
import com.merkator3.merkator3api.services.UserServiceImpl;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MerkatorUserServiceTests {

    @Autowired
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void createUserService() {
        userService = new UserServiceImpl(userRepository);
    }

    @AfterEach
    void deleteTestUsers() {
        if (userRepository.findByUsername("merkatorUser1")!=null){
            userRepository.delete(userRepository.findByUsername("merkatorUser1"));
        }
        if (userRepository.findByUsername("merkatorUser2")!=null){
            userRepository.delete(userRepository.findByUsername("merkatorUser2"));
        }
    }

    @Test
    void createUserTest() {
        userService.createUser("merkatorUser1");
        Assertions.assertEquals(userRepository.findByUsername("merkatorUser1").getUsername(), "merkatorUser1");
    }

    @Test
    void getUserTest() {
        userService.createUser("merkatorUser2");
        ObjectId testUserId = userRepository.findByUsername("merkatorUser2").getId();
        MerkatorUser testMerkatorUser = userService.getUser(testUserId);
        Assertions.assertEquals(testMerkatorUser.getId(),  testUserId);
    }
}
