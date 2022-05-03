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
import com.backend.store.dataobject.dto.CategoryDto;
import com.backend.store.dataobject.sqlentity.CategorySqlEntity;
import com.backend.store.dataobject.sqlrepository.CategorySqlRepository;
import com.backend.store.service.delete.CategoryDeleteService;
import com.backend.store.service.persistence.CategoryPersistenceService;
import com.backend.store.testutility.PersistentUtility;
import com.backend.store.testutility.TestClassAnnotation;

import java.util.Map;
import java.util.Optional;

@Slf4j
@TestClassAnnotation
@Transactional
public class CategoryPersistenceDeleteServiceTests {
    @Autowired
    CategoryPersistenceService categoryPersistenceService;
    @Autowired
    CategoryDeleteService categoryDeleteService;
    @Autowired
    CategorySqlRepository categorySqlRepository;

    @Test
      void test_createOne_dtoWithId() throws IncorrectDtoForCreationExc {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(515L);
        Assertions.assertThrows(IncorrectDtoForCreationExc.class, () -> categoryPersistenceService.createOne(categoryDto));
    }

    @Test
    @Order(1)
      void test_createOne_dtoWithoutId() throws IncorrectDtoForCreationExc {
        Map<String, Object> created = categoryPersistenceService.createOne(PersistentUtility.defaultCategoryDto());
        log.info((created).toString());
        log.info(categorySqlRepository.findAll().toString());
        Optional<CategorySqlEntity> categorySql = categorySqlRepository.findById(1L);

        Assertions.assertTrue(categorySql.isPresent());
    }

    @Test
      void test_updateOne_dtoWithoutId() {
        CategoryDto categoryDto = new CategoryDto();
        Assertions.assertThrows(IncorrectDtoForUpdateExc.class, () -> categoryPersistenceService.updateOne(categoryDto));
    }

    @Test
      void test_updateOne_dtoNotMatchEntity() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(15615L);
        Assertions.assertThrows(EntityWithIdNotFoundExc.class, () -> categoryPersistenceService.updateOne(categoryDto));
    }

    @Test
    @Order(2)
      void test_updateOne_dtoMatchEntity() throws IncorrectDtoForUpdateExc, EntityWithIdNotFoundExc {
        Optional<CategorySqlEntity> optCategory = categorySqlRepository.findById(1L);
        if (optCategory.isPresent()) {
            CategorySqlEntity originalEntity = optCategory.get();

            CategoryDto dtoModification = PersistentUtility.defaultCategoryDto();
            dtoModification.setId(originalEntity.getId());
            dtoModification.setRoom("room name");

            CategorySqlEntity entityUpdated = (CategorySqlEntity) categoryPersistenceService.updateOne(dtoModification).get("Sql");
            if (originalEntity != entityUpdated) {
                Assertions.assertTrue(true);
            }

        }
        Assertions.assertTrue(optCategory.isPresent());
    }

    @Test
    @Order(3)
      void test_deleteOneById_EntityExist() throws EntityWithIdNotFoundExc {
        categoryDeleteService.deleteById(1L);
        Assertions.assertFalse(categorySqlRepository.findById(1L).isPresent());
    }

    @Test
      void test_deleteOneById_EntityNotExist() {
        Assertions.assertThrows(EntityWithIdNotFoundExc.class, () -> categoryDeleteService.deleteById(51615L));
    }
}
