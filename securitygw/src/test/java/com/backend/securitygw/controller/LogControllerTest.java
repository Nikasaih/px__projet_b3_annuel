package com.backend.securitygw.controller;

import com.backend.securitygw.testutility.TestClassAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@Slf4j
@TestClassAnnotation
//Todo
public class LogControllerTest {
    final Long articleId = 1L;

    //Utility
    @LocalServerPort
    int port;
    TestRestTemplate template = new TestRestTemplate();

    @BeforeEach
    void setup() {
        String baseUrl = "http://localhost:" + port;
    }

    /*
    *     @Test
      void test_getOne_NotExist() {
        log.info("test_getOne_NotExist");
        ResponseEntity<ArticleSqlEntity> response = template.getForEntity(GET_ONE_ROUTE + "81515161", ArticleSqlEntity.class);
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode().value());
    }

    @Test
    @Order(1)
      void test_createOne() {
        ArticleDto articleDto = PersistentUtility.defaultArticleDto();
        ResponseEntity<?> response = template.postForEntity(CREATE_ONE_ROUTE, articleDto, ArticleSqlEntity.class);
        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode().value());
    }
    *     //deleteOne
    @Test
    @Order(10)
    void test_deleteOneById_Exist() {
        ResponseEntity<String> response = template.exchange(DELETE_ONE_ROUTE, HttpMethod.DELETE, null, String.class, articleId);
        Assertions.assertEquals(HttpStatus.ACCEPTED.value(), response.getStatusCode().value());
    }*/
}
