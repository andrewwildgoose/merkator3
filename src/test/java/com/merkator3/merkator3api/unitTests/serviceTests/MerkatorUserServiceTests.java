package com.merkator3.merkator3api.unitTests.serviceTests;

import com.merkator3.merkator3api.models.user.MerkatorUser;
import com.merkator3.merkator3api.repositories.RouteRepository;
import com.merkator3.merkator3api.repositories.UserRepository;
import com.merkator3.merkator3api.services.user.UserService;
import com.merkator3.merkator3api.services.user.UserServiceImpl;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MerkatorUserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    void createUserService() {
        userService = new UserServiceImpl(userRepository);
    }

    @AfterEach
    void deleteTestUsers() {
        if (userRepository.findByEmail("merkatorUser1@google.com")!=null){
            userRepository.delete(userRepository.findByEmail("merkatorUser1@google.com"));
        }
        if (userRepository.findByEmail("merkatorUser2@google.com")!=null){
            userRepository.delete(userRepository.findByEmail("merkatorUser2@google.com"));
        }
    }

    @Test
    void createUserTest() {
        userService.createUser("merkatorUser1@google.com", "1234");
        Assertions.assertEquals(userRepository.findByEmail("merkatorUser1@google.com").getUsername(), "merkatorUser1");
    }

    @Test
    void getUserTest() {
        userService.createUser("merkatorUser2@google.com", "1234");
        ObjectId testUserId = userRepository.findByEmail("\"merkatorUser2@google.com\"").getId();
        MerkatorUser testMerkatorUser = userService.getUser(testUserId);
        Assertions.assertEquals(testMerkatorUser.getId(),  testUserId);
    }
}
