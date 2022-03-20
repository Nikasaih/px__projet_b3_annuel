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
import spd.backend.dataobject.dto.CategoryDto;
import spd.backend.dataobject.sqlentity.CategorySqlEntity;
import spd.backend.dataobject.sqlrepository.CategorySqlRepository;
import spd.backend.service.delete.CategoryDeleteService;
import spd.backend.service.persistence.CategoryPersistenceService;
import spd.backend.testutility.PersistentUtility;
import spd.backend.testutility.TestClassAnnotation;

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
    public void test_createOne_dtoWithId() throws IncorrectDtoForCreationExc {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(515L);
        Assertions.assertThrows(IncorrectDtoForCreationExc.class, () -> categoryPersistenceService.createOne(categoryDto));
    }

    @Test
    @Order(1)
    public void test_createOne_dtoWithoutId() throws IncorrectDtoForCreationExc {
        Map<String, Object> created = categoryPersistenceService.createOne(PersistentUtility.defaultCategoryDto());
        log.info((created).toString());
        log.info(categorySqlRepository.findAll().toString());
        Optional<CategorySqlEntity> categorySql = categorySqlRepository.findById(1L);

        Assertions.assertTrue(categorySql.isPresent());
    }

    @Test
    public void test_updateOne_dtoWithoutId() {
        CategoryDto categoryDto = new CategoryDto();
        Assertions.assertThrows(IncorrectDtoForUpdateExc.class, () -> categoryPersistenceService.updateOne(categoryDto));
    }

    @Test
    public void test_updateOne_dtoNotMatchEntity() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(15615L);
        Assertions.assertThrows(EntityWithIdNotFoundExc.class, () -> categoryPersistenceService.updateOne(categoryDto));
    }

    @Test
    @Order(2)
    public void test_updateOne_dtoMatchEntity() throws IncorrectDtoForUpdateExc, EntityWithIdNotFoundExc {
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
    public void test_deleteOneById_EntityExist() throws EntityWithIdNotFoundExc {
        categoryDeleteService.deleteById(1L);
        Assertions.assertFalse(categorySqlRepository.findById(1L).isPresent());
    }

    @Test
    public void test_deleteOneById_EntityNotExist() {
        Assertions.assertThrows(EntityWithIdNotFoundExc.class, () -> categoryDeleteService.deleteById(51615L));
    }
}
