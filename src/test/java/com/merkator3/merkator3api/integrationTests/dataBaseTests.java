package com.merkator3.merkator3api.integrationTests;

import com.merkator3.merkator3api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class dataBaseTests {

    @Autowired
    private UserRepository userRepository;

}
