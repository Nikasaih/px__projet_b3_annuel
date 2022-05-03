package com.backend.store.controller;

import com.backend.store.dataobject.dto.MaterialDto;
import com.backend.store.dataobject.sqlentity.MaterialSqlEntity;
import com.backend.store.testutility.PersistentUtility;
import com.backend.store.testutility.TestClassAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
@TestClassAnnotation
public class MaterialControllerTests {
    final Long materialId = 1L;

    //Utility
    @LocalServerPort
    int port;
    TestRestTemplate template = new TestRestTemplate();

    //Route
    private String CREATE_ONE_ROUTE;
    private String UPDATE_ONE_ROUTE;
    private String GET_ALL_ROUTE;
    private String GET_ONE_ROUTE;
    private String DELETE_ONE_ROUTE;

    @BeforeEach
      void setup() {
        String baseUrl = "http://localhost:" + port;
        CREATE_ONE_ROUTE = baseUrl + "/api/materials";
        UPDATE_ONE_ROUTE = CREATE_ONE_ROUTE;
        GET_ALL_ROUTE = baseUrl + "/api/materials";
        GET_ONE_ROUTE = baseUrl + "/api/materials/";
        DELETE_ONE_ROUTE = baseUrl + "/api/materials/{id}";
    }

    @Test
      void test_getAll() {
        log.info("test_getAll");
        ResponseEntity<MaterialSqlEntity[]> response = template.getForEntity(GET_ALL_ROUTE, MaterialSqlEntity[].class);
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());

    }

    //getOne
    @Test
    @Order(3)
      void test_getOne_Exist() {
        log.info("test_getOne_Exist");
        ResponseEntity<MaterialSqlEntity> response = template.getForEntity(GET_ONE_ROUTE + materialId, MaterialSqlEntity.class);
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());

    }

    @Test
      void test_getOne_NotExist() {
        log.info("test_getOne_NotExist");
        ResponseEntity<MaterialSqlEntity> response = template.getForEntity(GET_ONE_ROUTE + "81515161", MaterialSqlEntity.class);
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode().value());

    }

    @Test
    @Order(1)
      void test_createOne() {
        MaterialDto materialDto = PersistentUtility.defaultMaterialDto();
        ResponseEntity<?> response = template.postForEntity(CREATE_ONE_ROUTE, materialDto, MaterialSqlEntity.class);
        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode().value());

    }

    @Test
      void test_createOne_badBinding() {
        MaterialDto materialDto = PersistentUtility.faultMaterialDto();
        ResponseEntity<String> response = template.postForEntity(CREATE_ONE_ROUTE, materialDto, String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode().value());
    }

    @Test
    @Order(2)
      void test_updateOne() {
        MaterialDto materialDto = PersistentUtility.defaultMaterialDto();
        materialDto.setId(materialId);

        ResponseEntity<MaterialSqlEntity> response = template.postForEntity(UPDATE_ONE_ROUTE, materialDto, MaterialSqlEntity.class);
        Assertions.assertEquals(HttpStatus.ACCEPTED.value(), response.getStatusCode().value());
    }

    @Test
    @Order(2)
      void test_updateOne_badBinding() {
        MaterialDto materialDto = PersistentUtility.faultMaterialDto();
        materialDto.setId(materialId);

        ResponseEntity<?> response = template.postForEntity(UPDATE_ONE_ROUTE, materialDto, Object.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode().value());
    }


    //deleteOne
    @Test
    @Order(10)
      void test_deleteOneById_Exist() {
        ResponseEntity<String> response = template.exchange(DELETE_ONE_ROUTE, HttpMethod.DELETE, null, String.class, materialId);
        Assertions.assertEquals(HttpStatus.ACCEPTED.value(), response.getStatusCode().value());
    }

    // delete by id 404
    @Test
    @Order(11)
      void test_deleteOneById_NotExist() {
        ResponseEntity<String> response = template.exchange(DELETE_ONE_ROUTE, HttpMethod.DELETE, null, String.class, 81515161L);
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode().value());
    }
}
