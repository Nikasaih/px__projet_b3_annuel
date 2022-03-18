package spd.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import spd.backend.BackendApplication;
import spd.backend.dataobject.dto.ArticleDto;
import spd.backend.dataobject.sqlentity.ArticleSqlEntity;

import static org.hamcrest.Matchers.is;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestArticleController {
    //url to test
    @LocalServerPort
    int port;
    TestRestTemplate template = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();
    //ut
    private String POST_ONE;
    private String GET_ALL;
    private String GET_ONE;
    private String DELETE_ONE;


    @Before
    public void setup() {
        String baseUrl = "http://localhost:" + port;
        POST_ONE = baseUrl + "/api/articles";
        GET_ALL = baseUrl + "/api/articles";
        GET_ONE = baseUrl + "/api/articles";
        DELETE_ONE = baseUrl + "/api/articles";
    }

    @Test
    public void test_getAll() {
        ResponseEntity<ArticleSqlEntity[]> response = template.getForEntity(GET_ALL, ArticleSqlEntity[].class);
        MatcherAssert.assertThat(response.getStatusCode().value(), is(HttpStatus.OK.value()));
    }

    //getOne
    @Test
    public void test_getOne_Exist() {
        ResponseEntity<ArticleSqlEntity> response = template.getForEntity(GET_ONE + "/1", ArticleSqlEntity.class);
        MatcherAssert.assertThat(response.getStatusCode().value(), is(HttpStatus.OK.value()));
    }

    @Test
    public void test_getOne_NotExist() {
        ResponseEntity<ArticleSqlEntity> response = template.getForEntity(GET_ONE + "/2", ArticleSqlEntity.class);
        MatcherAssert.assertThat(response.getStatusCode().value(), is(HttpStatus.NOT_FOUND.value()));
    }

    //createOne
    @Test
    public void test_createOne_WithoutRole() {
        ResponseEntity<?> response = template.postForEntity(POST_ONE, new ArticleDto(), ArticleSqlEntity.class);
        MatcherAssert.assertThat(response.getStatusCode().value(), is(HttpStatus.UNAUTHORIZED.value()));
    }

    @Test
    public void test_createOne_WithUser() {
        //Todo Connect as User
        ResponseEntity<?> response = template.postForEntity(POST_ONE, new ArticleDto(), ArticleSqlEntity.class);
        MatcherAssert.assertThat(response.getStatusCode().value(), is(HttpStatus.UNAUTHORIZED.value()));
    }

    @Test
    public void test_createOne_WithAdmin() {
        //Todo Connect as Admin
        ResponseEntity<?> response = template.postForEntity(POST_ONE, new ArticleDto(), ArticleSqlEntity.class);
        MatcherAssert.assertThat(response.getStatusCode().value(), is(HttpStatus.CREATED.value()));
    }

    //deleteOne

}
