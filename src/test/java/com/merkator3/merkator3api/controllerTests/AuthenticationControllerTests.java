package com.merkator3.merkator3api.controllerTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.setAllowComparingPrivateFields;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    // Obtain the test JWT from the application settings.
    // N.B. Will need updating if expired.
    @Value("${merkator.api.testJwt}")
    private String testJwt;

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
                "http://localhost:" + port + "/api/v1/auth/register",
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
        String email = "jess@google.com";
        String password = "secret";

        // Set the JSON values in the request body
        String requestBody = "{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}";

        // Set the headers to indicate JSON content
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create a request entity with the JSON body and headers
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Make a POST request to the authentication endpoint
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/auth/authenticate",
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
        // Obtain the test JWT from the application settings. Will need updating if expired.
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
}
