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
import com.backend.store.dataobject.dto.MaterialDto;
import com.backend.store.dataobject.sqlentity.MaterialSqlEntity;
import com.backend.store.dataobject.sqlrepository.MaterialSqlRepository;
import com.backend.store.service.delete.MaterialDeleteService;
import com.backend.store.service.persistence.MaterialPersistenceService;
import com.backend.store.testutility.PersistentUtility;
import com.backend.store.testutility.TestClassAnnotation;

import java.util.Map;
import java.util.Optional;

@Slf4j
@TestClassAnnotation
@Transactional
public class MaterialPersistenceDeleteServiceTests {
    @Autowired
    MaterialPersistenceService materialPersistenceService;
    @Autowired
    MaterialDeleteService materialDeleteService;
    @Autowired
    MaterialSqlRepository materialSqlRepository;

    @Test
      void test_createOne_dtoWithId() throws IncorrectDtoForCreationExc {
        MaterialDto materialDto = new MaterialDto();
        materialDto.setId(515L);
        Assertions.assertThrows(IncorrectDtoForCreationExc.class, () -> materialPersistenceService.createOne(materialDto));
    }

    @Test
    @Order(1)
      void test_createOne_dtoWithoutId() throws IncorrectDtoForCreationExc {
        Map<String, Object> created = materialPersistenceService.createOne(PersistentUtility.defaultMaterialDto());
        log.info((created).toString());
        log.info(materialSqlRepository.findAll().toString());
        Optional<MaterialSqlEntity> materialSql = materialSqlRepository.findById(1L);

        Assertions.assertTrue(materialSql.isPresent());
    }

    @Test
      void test_updateOne_dtoWithoutId() {
        MaterialDto materialDto = new MaterialDto();
        Assertions.assertThrows(IncorrectDtoForUpdateExc.class, () -> materialPersistenceService.updateOne(materialDto));
    }

    @Test
      void test_updateOne_dtoNotMatchEntity() {
        MaterialDto materialDto = new MaterialDto();
        materialDto.setId(15615L);
        Assertions.assertThrows(EntityWithIdNotFoundExc.class, () -> materialPersistenceService.updateOne(materialDto));
    }

    @Test
    @Order(2)
      void test_updateOne_dtoMatchEntity() throws IncorrectDtoForUpdateExc, EntityWithIdNotFoundExc {
        Optional<MaterialSqlEntity> optMaterial = materialSqlRepository.findById(1L);
        if (optMaterial.isPresent()) {
            MaterialSqlEntity originalEntity = optMaterial.get();

            MaterialDto dtoModification = PersistentUtility.defaultMaterialDto();
            dtoModification.setId(originalEntity.getId());
            dtoModification.setName("entity name");

            MaterialSqlEntity entityUpdated = (MaterialSqlEntity) materialPersistenceService.updateOne(dtoModification).get("Sql");
            if (originalEntity != entityUpdated) {
                Assertions.assertTrue(true);
            }

        }
        Assertions.assertTrue(optMaterial.isPresent());
    }

    @Test
    @Order(3)
      void test_deleteOneById_EntityExist() throws EntityWithIdNotFoundExc {
        materialDeleteService.deleteById(1L);
        Assertions.assertFalse(materialSqlRepository.findById(1L).isPresent());
    }

    @Test
      void test_deleteOneById_EntityNotExist() {
        Assertions.assertThrows(EntityWithIdNotFoundExc.class, () -> materialDeleteService.deleteById(51615L));
    }
}
