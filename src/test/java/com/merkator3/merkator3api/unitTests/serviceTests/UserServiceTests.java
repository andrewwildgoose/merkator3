package com.merkator3.merkator3api.unitTests.serviceTests;

import com.merkator3.merkator3api.models.Route;
import com.merkator3.merkator3api.models.Trip;
import com.merkator3.merkator3api.models.User;
import com.merkator3.merkator3api.repositories.UserRepository;
import com.merkator3.merkator3api.services.UserService;
import com.merkator3.merkator3api.services.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void createUserService() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void createUserTest() {
        userService.createUser("merkatorUser1");
        Assertions.assertEquals(userRepository.findByUserName("merkatorUser1").getUserName(), "merkatorUser1");
    }
}
