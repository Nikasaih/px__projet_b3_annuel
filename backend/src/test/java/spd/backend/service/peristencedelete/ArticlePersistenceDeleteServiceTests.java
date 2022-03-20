package spd.backend.service.peristencedelete;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import spd.backend.common.exception.EntityWithIdNotFoundExc;
import spd.backend.common.exception.IncorrectDtoForCreationExc;
import spd.backend.common.exception.IncorrectDtoForUpdateExc;
import spd.backend.dataobject.dto.ArticleDto;
import spd.backend.dataobject.sqlentity.ArticleSqlEntity;
import spd.backend.dataobject.sqlrepository.ArticleSqlRepository;
import spd.backend.service.delete.ArticleDeleteService;
import spd.backend.service.persistence.ArticlePersistenceService;
import spd.backend.testutility.PersistentUtility;
import spd.backend.testutility.TestClassAnnotation;

import java.util.Map;
import java.util.Optional;

@Slf4j
@TestClassAnnotation
@Transactional
public class ArticlePersistenceDeleteServiceTests {
    @Autowired
    ArticlePersistenceService articlePersistenceService;
    @Autowired
    ArticleDeleteService articleDeleteService;
    @Autowired
    ArticleSqlRepository articleSqlRepository;

    @Test
    public void test_createOne_dtoWithId() throws IncorrectDtoForCreationExc {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setId(515L);
        Assertions.assertThrows(IncorrectDtoForCreationExc.class, () -> articlePersistenceService.createOne(articleDto));
    }

    @Test
    @Order(1)
    public void test_createOne_dtoWithoutId() throws IncorrectDtoForCreationExc {
        Map<String, Object> created = articlePersistenceService.createOne(PersistentUtility.defaultArticleDto());
        log.info((created).toString());
        log.info(articleSqlRepository.findAll().toString());
        Optional<ArticleSqlEntity> articleSql = articleSqlRepository.findById(1L);

        Assertions.assertTrue(articleSql.isPresent());
    }

    @Test
    public void test_updateOne_dtoWithoutId() {
        ArticleDto articleDto = new ArticleDto();
        Assertions.assertThrows(IncorrectDtoForUpdateExc.class, () -> articlePersistenceService.updateOne(articleDto));
    }

    @Test
    public void test_updateOne_dtoNotMatchEntity() {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setId(15615L);
        Assertions.assertThrows(EntityWithIdNotFoundExc.class, () -> articlePersistenceService.updateOne(articleDto));
    }

    @Test
    @Order(2)
    public void test_updateOne_dtoMatchEntity() throws IncorrectDtoForUpdateExc, EntityWithIdNotFoundExc {
        Optional<ArticleSqlEntity> optArticle = articleSqlRepository.findById(1L);
        if (optArticle.isPresent()) {
            ArticleSqlEntity article = optArticle.get();
            ArticleDto dtoModification = PersistentUtility.defaultArticleDto();
            dtoModification.setId(article.getId());
            dtoModification.setName("ifjfij");
            Map<String, Object> updateResponse = articlePersistenceService.updateOne(dtoModification);

            Assertions.assertNotEquals(article, dtoModification);

        }
        Assertions.assertTrue(optArticle.isPresent());
    }

    @Test
    @Order(3)
    public void test_deleteOneById_EntityExist() throws EntityWithIdNotFoundExc {
        articleDeleteService.deleteById(1L);
        Assertions.assertFalse(articleSqlRepository.findById(1L).isPresent());
    }

    @Test
    public void test_deleteOneById_EntityNotExist() {
        Assertions.assertThrows(EntityWithIdNotFoundExc.class, () -> articleDeleteService.deleteById(51615L));
    }
}
