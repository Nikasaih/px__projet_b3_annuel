package spd.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import spd.backend.dataobject.dto.MaterialDto;
import spd.backend.dataobject.sqlentity.MaterialSqlEntity;
import spd.backend.service.AppUserService;
import spd.backend.testutility.AuthUtility;
import spd.backend.testutility.PersistentUtility;
import spd.backend.testutility.TestClassAnnotation;

@Slf4j
@TestClassAnnotation
public class MaterialControllerTests {
    final Long materialId = 1L;

    //Utility
    @LocalServerPort
    int port;
    TestRestTemplate template = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

    //Services
    @Autowired
    AppUserService appUserService;
    //Route
    private String CREATE_ONE;
    private String UPDATE_ONE;
    private String GET_ALL;
    private String GET_ONE;
    private String DELETE_ONE;

    @BeforeEach
    public void setup() {
        String baseUrl = "http://localhost:" + port;
        CREATE_ONE = baseUrl + "/api/materials";
        UPDATE_ONE = CREATE_ONE;
        GET_ALL = baseUrl + "/api/materials";
        GET_ONE = baseUrl + "/api/materials/";
        DELETE_ONE = baseUrl + "/api/materials/{id}";

        try {
            AuthUtility.registerAdmin(appUserService);

        } catch (Exception e) {
            log.info(e.toString());
        }
    }

    @AfterEach
    public void cleanAuth() {
        template.withBasicAuth("", "");
    }

    @Test
    public void test_getAll() {
        log.info("test_getAll");
        ResponseEntity<MaterialSqlEntity[]> response = template.getForEntity(GET_ALL, MaterialSqlEntity[].class);
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());

    }

    //getOne
    @Test
    @Order(3)
    public void test_getOne_Exist() {
        log.info("test_getOne_Exist");
        ResponseEntity<MaterialSqlEntity> response = template.getForEntity(GET_ONE + materialId, MaterialSqlEntity.class);
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());

    }

    @Test
    public void test_getOne_NotExist() {
        log.info("test_getOne_NotExist");
        ResponseEntity<MaterialSqlEntity> response = template.getForEntity(GET_ONE + "81515161", MaterialSqlEntity.class);
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode().value());

    }

    @Test
    public void test_createOne_WithoutRole() {
        log.info("test_createOne_WithoutRole");
        ResponseEntity<?> response = template.postForEntity(CREATE_ONE, new MaterialDto(), MaterialSqlEntity.class);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode().value());

    }

    @Test
    public void test_createOne_WithUser() {
        log.info("test_createOne_WithUser");
        //Todo Connect as User
        ResponseEntity<MaterialSqlEntity> response = template.postForEntity(CREATE_ONE, new MaterialDto(), MaterialSqlEntity.class);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode().value());
    }

    @Test
    @Order(1)
    public void test_createOne_WithAdmin() {
        log.info("test_createOne_WithAdmin");
        template = AuthUtility.connectAsAdmin();

        MaterialDto materialDto = PersistentUtility.defaultMaterialDto();
        ResponseEntity<?> response = template.postForEntity(CREATE_ONE, materialDto, MaterialSqlEntity.class);
        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode().value());

    }

    @Test
    public void test_createOne_WithAdmin_BadBinding() {
        log.info("test_createOne_WithAdmin");
        template = AuthUtility.connectAsAdmin();

        MaterialDto materialDto = PersistentUtility.defaultMaterialDto();
        materialDto.setArticlesId(null);
        ResponseEntity<String> response = template.postForEntity(CREATE_ONE, materialDto, String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode().value());
    }

    @Test
    @Order(2)
    public void test_updateOne_WithAdmin() {
        log.info("test_updateOne_WithAdmin");
        template = AuthUtility.connectAsAdmin();

        MaterialDto materialDto = PersistentUtility.defaultMaterialDto();
        materialDto.setId(materialId);

        ResponseEntity<MaterialSqlEntity> response = template.postForEntity(UPDATE_ONE, materialDto, MaterialSqlEntity.class);
        Assertions.assertEquals(HttpStatus.ACCEPTED.value(), response.getStatusCode().value());
    }


    //deleteOne
    @Test
    @Order(10)
    public void test_deleteOneById_Exist() {
        log.info("deleteOneByIdExist");
        template = AuthUtility.connectAsAdmin();
        ResponseEntity<String> response = template.exchange(DELETE_ONE, HttpMethod.DELETE, null, String.class, materialId);
        Assertions.assertEquals(HttpStatus.ACCEPTED.value(), response.getStatusCode().value());
    }

    // delete by id 404
    @Test
    @Order(11)
    public void test_deleteOneById_NotExist() {
        log.info("deleteOneByIdNotExist");
        template = AuthUtility.connectAsAdmin();
        ResponseEntity<String> response = template.exchange(DELETE_ONE, HttpMethod.DELETE, null, String.class, 81515161L);
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode().value());
    }
}