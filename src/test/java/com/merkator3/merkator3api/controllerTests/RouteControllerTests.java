package com.merkator3.merkator3api.controllerTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RouteControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void routeGpxTest() {
        assertThat(restTemplate.getForObject("http://localhost:" + port + "/merkator/user/64a96ca81716a23702501f57/route/64cd0aae3211936cddad0635",
                String.class)).contains("Hello World from Spring Boot");
    }

    @Test
    public void routeGpxStringTest() {
        System.out.println();
        assertThat(restTemplate.getForObject("http://localhost:" + port + "/merkator/user/64a96ca81716a23702501f57/route_gpx_string/64cd0aae3211936cddad0635",
                String.class)).contains("Hello World from Spring Boot");
    }
}

