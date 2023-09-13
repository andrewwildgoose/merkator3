package com.merkator3.merkator3api.controllerTests;

import com.merkator3.merkator3api.authentication.JwtService;
import com.merkator3.merkator3api.models.user.MerkatorUser;
import com.merkator3.merkator3api.models.user.Role;
import com.merkator3.merkator3api.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JwtService jwtService;
    private String testJwt;

    MerkatorUser user;

    @BeforeEach
    void createTestUser(){
        user = MerkatorUser.builder()
                .name("test")
                .email("test@mail.com")
                .password(passwordEncoder.encode("password"))
                .role(Role.USER)
                .build();
        user.addRoute(new ObjectId());
        userRepository.save(user);
        testJwt = jwtService.generateToken(user);
    }
    @AfterEach
    void removeUser() {
        if (userRepository.findByEmail("test@mail.com") != null) {
            userRepository.deleteById(userRepository.findByEmail("test@mail.com").getId().toString());
        }
    }
    @Test
    void getUserDetailsTest() {
        // Make a GET request to the secured endpoint using the authenticated user's JWT
        HttpHeaders headers = new HttpHeaders();
        // Obtain the test JWT from the application settings. Will need updating if expired.
        headers.set("Authorization", "Bearer " + testJwt);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/merkator/user/details",
                HttpMethod.GET,
                requestEntity,
                String.class
        );

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).contains("\"role\":\"USER\"");
    }
}

