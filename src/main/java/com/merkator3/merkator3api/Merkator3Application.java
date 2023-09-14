package com.merkator3.merkator3api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class Merkator3Application {

    public static void main(String[] args) {
        SpringApplication.run(Merkator3Application.class, args);
    }


}
