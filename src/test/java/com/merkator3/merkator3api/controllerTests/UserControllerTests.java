package com.merkator3.merkator3api.controllerTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    // Obtain the test JWT from the application settings.
    // N.B. Will need updating if expired.
    @Value("${merkator.api.testJwt}")
    private String testJwt;

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

    @Test
    void getUserRoutesTest() {
        // Make a GET request to the secured endpoint using the authenticated user's JWT
        HttpHeaders headers = new HttpHeaders();
        // Obtain the test JWT from the application settings. Will need updating if expired.
        headers.set("Authorization", "Bearer " + testJwt);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/merkator/user/routes",
                HttpMethod.GET,
                requestEntity,
                String.class
        );

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).contains("\"role\":\"USER\"");
    }
}

