package spd.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import spd.backend.TestUtility;
import spd.backend.dataobject.dto.ArticleDto;
import spd.backend.dataobject.sqlentity.ArticleSqlEntity;
import spd.backend.dataobject.sqlrepository.ArticleSqlRepository;
import spd.backend.service.AppUserService;

import java.util.HashSet;

import static org.hamcrest.Matchers.is;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ArticleControllerTests {
    final static String ADMIN_EMAIL = "test@test.com";
    final static String ADMIN_PASSWORD = "testPassword";
    final Long articleId = 1L;

    //url to test
    @LocalServerPort
    int port;
    TestRestTemplate template = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();
    @Autowired
    ArticleSqlRepository articleSqlRepository;
    @Autowired
    AppUserService appUserService;
    //ut
    private String CREATE_ONE;
    private String UPDATE_ONE;
    private String GET_ALL;
    private String GET_ONE;
    private String DELETE_ONE;

    @BeforeEach
    public void setup() {
        String baseUrl = "http://localhost:" + port;
        CREATE_ONE = baseUrl + "/api/articles";
        UPDATE_ONE = CREATE_ONE;
        GET_ALL = baseUrl + "/api/articles";
        GET_ONE = baseUrl + "/api/articles/";
        DELETE_ONE = baseUrl + "/api/articles/{id}";

        try {
            TestUtility.registerAdmin(appUserService);

        } catch (Exception e) {
            log.info(e.toString());
        }
    }

    @AfterEach
    public void t() {
        template.withBasicAuth("", "");
    }

    @Test
    @WithAnonymousUser
    public void test_getAll() {
        log.info("test_getAll");
        ResponseEntity<ArticleSqlEntity[]> response = template.getForEntity(GET_ALL, ArticleSqlEntity[].class);
        MatcherAssert.assertThat(response.getStatusCode().value(), is(HttpStatus.OK.value()));
    }

    //getOne
    @Test
    @Order(3)
    public void test_getOne_Exist() {
        log.info("test_getOne_Exist");
        ResponseEntity<ArticleSqlEntity> response = template.getForEntity(GET_ONE + articleId, ArticleSqlEntity.class);
        MatcherAssert.assertThat(response.getStatusCode().value(), is(HttpStatus.OK.value()));
    }

    @Test
    public void test_getOne_NotExist() {
        log.info("test_getOne_NotExist");
        ResponseEntity<ArticleSqlEntity> response = template.getForEntity(GET_ONE + "81515161", ArticleSqlEntity.class);
        MatcherAssert.assertThat(response.getStatusCode().value(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void test_createOne_WithoutRole() {
        log.info("test_createOne_WithoutRole");
        ResponseEntity<?> response = template.postForEntity(CREATE_ONE, new ArticleDto(), ArticleSqlEntity.class);
        MatcherAssert.assertThat(response.getStatusCode().value(), is(HttpStatus.UNAUTHORIZED.value()));
    }

    @Test
    public void test_createOne_WithUser() {
        log.info("test_createOne_WithUser");
        //Todo Connect as User
        ResponseEntity<ArticleSqlEntity> response = template.postForEntity(CREATE_ONE, new ArticleDto(), ArticleSqlEntity.class);
        MatcherAssert.assertThat(response.getStatusCode().value(), is(HttpStatus.UNAUTHORIZED.value()));
    }

    @Test
    @Order(1)
    public void test_createOne_WithAdmin() {
        log.info("test_createOne_WithAdmin");
        template = TestUtility.connectAsAdmin();

        ArticleDto articleDto = defaultArticleDto();
        ResponseEntity<?> response = template.postForEntity(CREATE_ONE, articleDto, ArticleSqlEntity.class);
        MatcherAssert.assertThat(response.getStatusCode().value(), is(HttpStatus.CREATED.value()));
    }

    @Test
    public void test_createOne_WithAdmin_BadBinding() {
        log.info("test_createOne_WithAdmin");
        template = TestUtility.connectAsAdmin();

        ArticleDto articleDto = defaultArticleDto();
        articleDto.setMaterialsId(null);
        ResponseEntity<String> response = template.postForEntity(CREATE_ONE, articleDto, String.class);
        MatcherAssert.assertThat(response.getStatusCode().value(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    @Order(2)
    public void test_updateOne_WithAdmin() {
        log.info("test_updateOne_WithAdmin");
        template = TestUtility.connectAsAdmin();

        ArticleDto articleDto = defaultArticleDto();
        articleDto.setId(articleId);

        ResponseEntity<ArticleSqlEntity> response = template.postForEntity(UPDATE_ONE, articleDto, ArticleSqlEntity.class);
        MatcherAssert.assertThat(response.getStatusCode().value(), is(HttpStatus.ACCEPTED.value()));
    }

    private ArticleDto defaultArticleDto() {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setCategoriesId(new HashSet<>());
        articleDto.setColorsId(new HashSet<>());
        articleDto.setCommentsId(new HashSet<>());
        articleDto.setMaterialsId(new HashSet<>());
        return articleDto;
    }


    //deleteOne
    @Test
    @Order(10)
    public void test_deleteOneById_Exist() {
        log.info("deleteOneByIdExist");
        template = TestUtility.connectAsAdmin();
        ResponseEntity<String> response = template.exchange(DELETE_ONE, HttpMethod.DELETE, null, String.class, articleId);
        MatcherAssert.assertThat(response.getStatusCode().value(), is(HttpStatus.OK.value()));
    }

    // delete by id 404
    @Test
    @Order(11)
    public void test_deleteOneById_NotExist() {
        log.info("deleteOneByIdNotExist");
        template = TestUtility.connectAsAdmin();
        ResponseEntity<String> response = template.exchange(DELETE_ONE, HttpMethod.DELETE, null, String.class, 81515161L);
        MatcherAssert.assertThat(response.getStatusCode().value(), is(HttpStatus.NOT_FOUND.value()));
    }

}
