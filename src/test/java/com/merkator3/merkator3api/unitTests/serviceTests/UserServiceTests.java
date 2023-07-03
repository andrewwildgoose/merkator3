package com.merkator3.merkator3api.unitTests.serviceTests;

import com.merkator3.merkator3api.models.Route;
import com.merkator3.merkator3api.models.Trip;
import com.merkator3.merkator3api.models.User;
import com.merkator3.merkator3api.repositories.UserRepository;
import com.merkator3.merkator3api.services.UserService;
import com.merkator3.merkator3api.services.UserServiceImpl;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.AfterTestExecution;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void createUserService() {
        userService = new UserServiceImpl(userRepository);
    }

    @AfterEach
    void deleteTestUsers() {
        if (userRepository.findByUserName("merkatorUser1")!=null){
            userRepository.delete(userRepository.findByUserName("merkatorUser1"));
        }
        if (userRepository.findByUserName("merkatorUser2")!=null){
            userRepository.delete(userRepository.findByUserName("merkatorUser2"));
        }
    }

    @Test
    void createUserTest() {
        userService.createUser("merkatorUser1");
        Assertions.assertEquals(userRepository.findByUserName("merkatorUser1").getUserName(), "merkatorUser1");
    }

    @Test
    void getUserTest() {
        userService.createUser("merkatorUser2");
        ObjectId testUserId = userRepository.findByUserName("merkatorUser2").getId();
        User testUser = userService.getUser(testUserId);
        Assertions.assertEquals(testUser.getId(),  testUserId);
    }
}
