package com.backend.storerate.controller;

import com.backend.storerate.testutility.TestClassAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
@TestClassAnnotation
public class BasketControllerTests {

    //Utility
    @LocalServerPort
    int port;
    TestRestTemplate template = new TestRestTemplate();
    //Route
    private Long customerId = 1L;
    private String CREATE_ONE;
    private String UPDATE_ONE;
    private String GET_ALL_BY_CUSTOMERID;
    private String DELETE_ONE;

    @BeforeEach
    public void setup() {
        String baseUrl = "http://localhost:" + port + "/basket";
        CREATE_ONE = baseUrl;
        UPDATE_ONE = baseUrl;
        GET_ALL_BY_CUSTOMERID = baseUrl + "/" + customerId;
        DELETE_ONE = baseUrl + "/remove";
    }

    @Test
    public void test_getCustomerBoxById() {
        ResponseEntity<Object> response = template.getForEntity(GET_ALL_BY_CUSTOMERID, Object.class);
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
    }
}
