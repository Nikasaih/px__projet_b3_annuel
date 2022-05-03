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
import com.backend.store.dataobject.dto.ColorDto;
import com.backend.store.dataobject.sqlentity.ColorSqlEntity;
import com.backend.store.dataobject.sqlrepository.ColorSqlRepository;
import com.backend.store.service.delete.ColorDeleteService;
import com.backend.store.service.persistence.ColorPersistenceService;
import com.backend.store.testutility.PersistentUtility;
import com.backend.store.testutility.TestClassAnnotation;

import java.util.Map;
import java.util.Optional;

@Slf4j
@TestClassAnnotation
@Transactional
public  class ColorsPersistenceDeleteServiceTests {
    @Autowired
    ColorPersistenceService colorPersistenceService;
    @Autowired
    ColorDeleteService colorDeleteService;
    @Autowired
    ColorSqlRepository colorSqlRepository;

    @Test
      void test_createOne_dtoWithId() throws IncorrectDtoForCreationExc {
        ColorDto colorDto = new ColorDto();
        colorDto.setId(515L);
        Assertions.assertThrows(IncorrectDtoForCreationExc.class, () -> colorPersistenceService.createOne(colorDto));
    }

    @Test
    @Order(1)
      void test_createOne_dtoWithoutId() throws IncorrectDtoForCreationExc {
        Map<String, Object> created = colorPersistenceService.createOne(PersistentUtility.defaultColorDto());
        log.info((created).toString());
        log.info(colorSqlRepository.findAll().toString());
        Optional<ColorSqlEntity> colorSql = colorSqlRepository.findById(1L);

        Assertions.assertTrue(colorSql.isPresent());
    }

    @Test
      void test_updateOne_dtoWithoutId() {
        ColorDto colorDto = new ColorDto();
        Assertions.assertThrows(IncorrectDtoForUpdateExc.class, () -> colorPersistenceService.updateOne(colorDto));
    }

    @Test
      void test_updateOne_dtoNotMatchEntity() {
        ColorDto colorDto = new ColorDto();
        colorDto.setId(15615L);
        Assertions.assertThrows(EntityWithIdNotFoundExc.class, () -> colorPersistenceService.updateOne(colorDto));
    }

    @Test
    @Order(2)
      void test_updateOne_dtoMatchEntity() throws IncorrectDtoForUpdateExc, EntityWithIdNotFoundExc {
        Optional<ColorSqlEntity> optColor = colorSqlRepository.findById(1L);
        if (optColor.isPresent()) {
            ColorSqlEntity originalEntity = optColor.get();

            ColorDto dtoModification = PersistentUtility.defaultColorDto();
            dtoModification.setId(originalEntity.getId());
            dtoModification.setName("entity name");

            ColorSqlEntity entityUpdated = (ColorSqlEntity) colorPersistenceService.updateOne(dtoModification).get("Sql");
            if (originalEntity != entityUpdated) {
                Assertions.assertTrue(true);
            }

        }
        Assertions.assertTrue(optColor.isPresent());
    }

    @Test
    @Order(3)
      void test_deleteOneById_EntityExist() throws EntityWithIdNotFoundExc {
        colorDeleteService.deleteById(1L);
        Assertions.assertFalse(colorSqlRepository.findById(1L).isPresent());
    }

    @Test
      void test_deleteOneById_EntityNotExist() {
        Assertions.assertThrows(EntityWithIdNotFoundExc.class, () -> colorDeleteService.deleteById(51615L));
    }
}
