package com.merkator3.merkator3api.unitTests;


import com.merkator3.merkator3api.models.User;
import com.merkator3.merkator3api.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class UserTests {

    @Test
    void userCreated() {

        User testUser = new User("testUser");
        Assertions.assertEquals(testUser.getUserName(), "testUser");

    }


}
