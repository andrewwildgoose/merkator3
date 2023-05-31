package com.merkator3.merkator3api;

import com.merkator3.merkator3api.models.User;
import com.merkator3.merkator3api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Merkator3Application implements CommandLineRunner {

    private final UserRepository userRepository;

    @Autowired
    public Merkator3Application(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public static void main(String[] args) {
        SpringApplication.run(Merkator3Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.findAll().isEmpty()){
            userRepository.save(new User("wildgoose"));
            userRepository.save(new User("campbell"));
        }

        for (User user : userRepository.findAll()){
            System.out.println(user.getUserName());
        }


    }
}
