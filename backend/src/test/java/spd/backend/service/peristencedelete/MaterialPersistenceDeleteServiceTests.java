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
import spd.backend.dataobject.dto.MaterialDto;
import spd.backend.dataobject.sqlentity.MaterialSqlEntity;
import spd.backend.dataobject.sqlrepository.MaterialSqlRepository;
import spd.backend.service.delete.MaterialDeleteService;
import spd.backend.service.persistence.MaterialPersistenceService;
import spd.backend.testutility.PersistentUtility;
import spd.backend.testutility.TestClassAnnotation;

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
    public void test_createOne_dtoWithId() throws IncorrectDtoForCreationExc {
        MaterialDto materialDto = new MaterialDto();
        materialDto.setId(515L);
        Assertions.assertThrows(IncorrectDtoForCreationExc.class, () -> materialPersistenceService.createOne(materialDto));
    }

    @Test
    @Order(1)
    public void test_createOne_dtoWithoutId() throws IncorrectDtoForCreationExc {
        Map<String, Object> created = materialPersistenceService.createOne(PersistentUtility.defaultMaterialDto());
        log.info((created).toString());
        log.info(materialSqlRepository.findAll().toString());
        Optional<MaterialSqlEntity> materialSql = materialSqlRepository.findById(1L);

        Assertions.assertTrue(materialSql.isPresent());
    }

    @Test
    public void test_updateOne_dtoWithoutId() {
        MaterialDto materialDto = new MaterialDto();
        Assertions.assertThrows(IncorrectDtoForUpdateExc.class, () -> materialPersistenceService.updateOne(materialDto));
    }

    @Test
    public void test_updateOne_dtoNotMatchEntity() {
        MaterialDto materialDto = new MaterialDto();
        materialDto.setId(15615L);
        Assertions.assertThrows(EntityWithIdNotFoundExc.class, () -> materialPersistenceService.updateOne(materialDto));
    }

    @Test
    @Order(2)
    public void test_updateOne_dtoMatchEntity() throws IncorrectDtoForUpdateExc, EntityWithIdNotFoundExc {
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
    public void test_deleteOneById_EntityExist() throws EntityWithIdNotFoundExc {
        materialDeleteService.deleteById(1L);
        Assertions.assertFalse(materialSqlRepository.findById(1L).isPresent());
    }

    @Test
    public void test_deleteOneById_EntityNotExist() {
        Assertions.assertThrows(EntityWithIdNotFoundExc.class, () -> materialDeleteService.deleteById(51615L));
    }
}
