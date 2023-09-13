package com.merkator3.merkator3api.controllerTests;

import com.merkator3.merkator3api.authentication.JwtService;
import com.merkator3.merkator3api.models.user.MerkatorUser;
import com.merkator3.merkator3api.models.user.Role;
import com.merkator3.merkator3api.repositories.UserRepository;
import com.merkator3.merkator3api.unitTests.serviceTests.MerkatorUserServiceTests;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
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
public class AuthenticationControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Value("${merkator.api.testJwt}")
    private String invalidTestJwt;

    MerkatorUser user;

    @BeforeEach
    void createTestUser(){
        user = MerkatorUser.builder()
                .name("test")
                .email("test@mail.com")
                .password(passwordEncoder.encode("password"))
                .role(Role.USER)
                .build();
        userRepository.save(user);
    }
    @AfterEach
    void removeUser() {
        if (userRepository.findByEmail("test@mail.com") != null){
            userRepository.deleteById(userRepository.findByEmail("test@mail.com").getId().toString());
        }
        if (userRepository.findByEmail("jess@google.com") != null){
            userRepository.deleteById(userRepository.findByEmail("jess@google.com").getId().toString());
        }
    }
    @Test
    public void registerUserTest() {
        // Create JSON values for email and password
        String email = "jess@google.com";
        String password = "secret";

        // Set the JSON values in the request body
        String requestBody = "{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}";

        // Set the headers to indicate JSON content
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create a request entity with the JSON body and headers
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Make a POST request with the JSON values in the request body
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/merkator/api/v1/auth/register",
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).contains("\"token\"");
    }

    @Test
    public void authenticateUserTest() {
        // Create JSON values for email and password
        String email = "test@mail.com";
        String password = "password";

        // Set the JSON values in the request body
        String requestBody = "{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}";

        // Set the headers to indicate JSON content
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create a request entity with the JSON body and headers
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Make a POST request to the authentication endpoint
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/merkator/api/v1/auth/authenticate",
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).contains("\"token\"");
    }

    @Test
    public void accessSecuredEndpointWithJWT() {
        // Make a GET request to the secured endpoint using the authenticated user's JWT
        HttpHeaders headers = new HttpHeaders();

        // Generate test user and JWT
        String testJwt = jwtService.generateToken(user);

        headers.set("Authorization", "Bearer " + testJwt);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/demo-controller",
                HttpMethod.GET,
                requestEntity,
                String.class
        );

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Hello from secured endpoint");
    }

    @Test
    public void accessDeniedWithInvalidTokenFormat() {
        // Make a GET request to the secured endpoint using the authenticated user's JWT
        HttpHeaders headers = new HttpHeaders();

        // Invalid token
        String testJwt = "invalidToken";

        headers.set("Authorization", "Bearer " + testJwt);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/demo-controller",
                HttpMethod.GET,
                requestEntity,
                String.class
        );

        assertThat(response.getStatusCodeValue()).isEqualTo(403);
    }

    @Test
    public void accessDeniedWithExpiredToken() {
        // Make a GET request to the secured endpoint using the authenticated user's JWT
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "Bearer " + invalidTestJwt);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/demo-controller",
                HttpMethod.GET,
                requestEntity,
                String.class
        );

        assertThat(response.getStatusCodeValue()).isEqualTo(403);
    }
}
