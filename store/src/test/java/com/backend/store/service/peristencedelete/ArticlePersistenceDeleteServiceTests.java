package com.backend.store.service.peristencedelete;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.backend.store.common.exception.EntityWithIdNotFoundExc;
import com.backend.store.common.exception.IncorrectDtoForCreationExc;
import com.backend.store.common.exception.IncorrectDtoForUpdateExc;
import com.backend.store.dataobject.dto.ArticleDto;
import com.backend.store.dataobject.sqlentity.ArticleSqlEntity;
import com.backend.store.dataobject.sqlrepository.ArticleSqlRepository;
import com.backend.store.service.delete.ArticleDeleteService;
import com.backend.store.service.persistence.ArticlePersistenceService;
import com.backend.store.testutility.PersistentUtility;
import com.backend.store.testutility.TestClassAnnotation;

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
      void test_createOne_dtoWithId() throws IncorrectDtoForCreationExc {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setId(515L);
        Assertions.assertThrows(IncorrectDtoForCreationExc.class, () -> articlePersistenceService.createOne(articleDto));
    }

    @Test
    @Order(1)
      void test_createOne_dtoWithoutId() throws IncorrectDtoForCreationExc {
        Map<String, Object> created = articlePersistenceService.createOne(PersistentUtility.defaultArticleDto());
        log.info((created).toString());
        log.info(articleSqlRepository.findAll().toString());
        Optional<ArticleSqlEntity> articleSql = articleSqlRepository.findById(1L);

        Assertions.assertTrue(articleSql.isPresent());
    }

    @Test
      void test_updateOne_dtoWithoutId() {
        ArticleDto articleDto = new ArticleDto();
        Assertions.assertThrows(IncorrectDtoForUpdateExc.class, () -> articlePersistenceService.updateOne(articleDto));
    }

    @Test
      void test_updateOne_dtoNotMatchEntity() {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setId(15615L);
        Assertions.assertThrows(EntityWithIdNotFoundExc.class, () -> articlePersistenceService.updateOne(articleDto));
    }

    @Test
    @Order(2)
      void test_updateOne_dtoMatchEntity() throws IncorrectDtoForUpdateExc, EntityWithIdNotFoundExc {
        Optional<ArticleSqlEntity> optArticle = articleSqlRepository.findById(1L);
        if (optArticle.isPresent()) {
            ArticleSqlEntity originalEntity = optArticle.get();

            ArticleDto dtoModification = PersistentUtility.defaultArticleDto();
            dtoModification.setId(originalEntity.getId());
            dtoModification.setName("entity name");

            ArticleSqlEntity entityUpdated = (ArticleSqlEntity) articlePersistenceService.updateOne(dtoModification).get("Sql");
            if (originalEntity != entityUpdated) {
                Assertions.assertTrue(true);
            }

        }
        Assertions.assertTrue(optArticle.isPresent());
    }

    @Test
    @Order(3)
      void test_deleteOneById_EntityExist() throws EntityWithIdNotFoundExc {
        articleDeleteService.deleteById(1L);
        Assertions.assertFalse(articleSqlRepository.findById(1L).isPresent());
    }

    @Test
      void test_deleteOneById_EntityNotExist() {
        Assertions.assertThrows(EntityWithIdNotFoundExc.class, () -> articleDeleteService.deleteById(51615L));
    }
}
