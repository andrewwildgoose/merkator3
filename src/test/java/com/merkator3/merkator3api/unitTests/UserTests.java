package com.merkator3.merkator3api.unitTests;


import com.merkator3.merkator3api.models.User;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class UserTests {

    @Test
    void userCreated() {

        User testUser = new User("testUser");
        Assertions.assertEquals(testUser.getUserName(), "testUser");

    }


}
