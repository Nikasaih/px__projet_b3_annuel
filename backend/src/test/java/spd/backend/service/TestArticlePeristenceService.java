package spd.backend.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import spd.backend.BackendApplication;
import spd.backend.common.exception.IncorrectDtoForCreation;
import spd.backend.dataobject.dto.ArticleDto;
import spd.backend.service.persistence.ArticlePersistenceService;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class TestArticlePeristenceService {
    @Autowired
    ArticlePersistenceService articlePersistenceService;

    @Test
    public void t() throws IncorrectDtoForCreation {
        Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> articlePersistenceService.createOne(new ArticleDto()));
    }
}
