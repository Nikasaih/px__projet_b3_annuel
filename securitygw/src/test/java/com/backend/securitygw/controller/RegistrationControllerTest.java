package com.backend.securitygw.controller;

import com.backend.securitygw.testutility.TestClassAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@Slf4j
@TestClassAnnotation
//Todo
public class RegistrationControllerTest {
    final Long articleId = 1L;

    //Utility
    @LocalServerPort
    int port;
    TestRestTemplate template = new TestRestTemplate();

    //Routes
    String REGISTER_ROUTE;

    @BeforeEach
    void setup() {
        String baseUrl = "http://localhost:" + port;

        REGISTER_ROUTE = baseUrl + "/api/registration";
    }

    //Post request on registerroute

    @Test
    public void test_register_badRequestBody() {
        // Assertions.;
    }

}
