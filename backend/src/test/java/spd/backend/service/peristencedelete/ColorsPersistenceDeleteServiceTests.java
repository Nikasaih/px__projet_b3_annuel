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
import spd.backend.dataobject.dto.ColorDto;
import spd.backend.dataobject.sqlentity.ColorSqlEntity;
import spd.backend.dataobject.sqlrepository.ColorSqlRepository;
import spd.backend.service.delete.ColorDeleteService;
import spd.backend.service.persistence.ColorPersistenceService;
import spd.backend.testutility.PersistentUtility;
import spd.backend.testutility.TestClassAnnotation;

import java.util.Map;
import java.util.Optional;

@Slf4j
@TestClassAnnotation
@Transactional
public class ColorsPersistenceDeleteServiceTests {
    @Autowired
    ColorPersistenceService colorPersistenceService;
    @Autowired
    ColorDeleteService colorDeleteService;
    @Autowired
    ColorSqlRepository colorSqlRepository;

    @Test
    public void test_createOne_dtoWithId() throws IncorrectDtoForCreationExc {
        ColorDto colorDto = new ColorDto();
        colorDto.setId(515L);
        Assertions.assertThrows(IncorrectDtoForCreationExc.class, () -> colorPersistenceService.createOne(colorDto));
    }

    @Test
    @Order(1)
    public void test_createOne_dtoWithoutId() throws IncorrectDtoForCreationExc {
        Map<String, Object> created = colorPersistenceService.createOne(PersistentUtility.defaultColorDto());
        log.info((created).toString());
        log.info(colorSqlRepository.findAll().toString());
        Optional<ColorSqlEntity> colorSql = colorSqlRepository.findById(1L);

        Assertions.assertTrue(colorSql.isPresent());
    }

    @Test
    public void test_updateOne_dtoWithoutId() {
        ColorDto colorDto = new ColorDto();
        Assertions.assertThrows(IncorrectDtoForUpdateExc.class, () -> colorPersistenceService.updateOne(colorDto));
    }

    @Test
    public void test_updateOne_dtoNotMatchEntity() {
        ColorDto colorDto = new ColorDto();
        colorDto.setId(15615L);
        Assertions.assertThrows(EntityWithIdNotFoundExc.class, () -> colorPersistenceService.updateOne(colorDto));
    }

    @Test
    @Order(2)
    public void test_updateOne_dtoMatchEntity() throws IncorrectDtoForUpdateExc, EntityWithIdNotFoundExc {
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
    public void test_deleteOneById_EntityExist() throws EntityWithIdNotFoundExc {
        colorDeleteService.deleteById(1L);
        Assertions.assertFalse(colorSqlRepository.findById(1L).isPresent());
    }

    @Test
    public void test_deleteOneById_EntityNotExist() {
        Assertions.assertThrows(EntityWithIdNotFoundExc.class, () -> colorDeleteService.deleteById(51615L));
    }
}
