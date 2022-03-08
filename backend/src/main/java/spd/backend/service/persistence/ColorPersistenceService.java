package spd.backend.service.persistence;

import spd.backend.common.exception.EntityWithIdNotFound;
import spd.backend.common.exception.IncorrectDtoForCreation;
import spd.backend.common.exception.IncorrectDtoForUpdate;
import spd.backend.dataobject.dto.ColorDto;
import spd.backend.dataobject.sqlentity.ArticleSqlEntity;
import spd.backend.dataobject.sqlentity.ColorSqlEntity;
import spd.backend.dataobject.sqlrepository.ArticleSqlRepository;
import spd.backend.dataobject.sqlrepository.ColorSqlRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ColorPersistenceService {
    final ModelMapper mapper = new ModelMapper();
    //Bean
    @Autowired
    ColorSqlRepository colorSqlRepository;
    @Autowired
    ArticleSqlRepository articleSqlRepository;

    public Map<String, Object> createOne(final ColorDto colorToCreateInDb) throws IncorrectDtoForCreation {
        if (colorToCreateInDb.getId() != null) {
            throw new IncorrectDtoForCreation();
        }
        return persistEntity(colorToCreateInDb);
    }

    public Map<String, Object> updateOne(final ColorDto colorToUpdateInDb) throws IncorrectDtoForUpdate, EntityWithIdNotFound {
        if (colorToUpdateInDb.getId() == null) {
            throw new IncorrectDtoForUpdate();
        }
        Optional<ColorSqlEntity> colorSaved = colorSqlRepository.findById(colorToUpdateInDb.getId());
        if (colorSaved.isEmpty()) {

            throw new EntityWithIdNotFound(colorToUpdateInDb.getId(), "Color");
        }

        return persistEntity(colorToUpdateInDb);
    }

    private Map<String, Object> persistEntity(ColorDto colorToPersistInDb) {
        ColorSqlEntity colorToSaveInSql;
        colorToSaveInSql = mapper.map(colorToPersistInDb, ColorSqlEntity.class);

        Iterable<ArticleSqlEntity> bookIterable = articleSqlRepository.findAllById(colorToPersistInDb.getArticlesId());

        Map<String, Object> map = new HashMap<>();
        map.put("Sql", saveInSql(colorToSaveInSql, bookIterable));
        return map;
    }


    private ColorSqlEntity saveInSql(ColorSqlEntity colorToSave, Iterable<ArticleSqlEntity> articleSqlEntityIterable) {
        Set<ArticleSqlEntity> articleCollection = new HashSet<>();
        articleSqlEntityIterable.iterator().forEachRemaining(e -> articleCollection.add(e));

        colorToSave.setArticles(articleCollection);
        return colorSqlRepository.save(colorToSave);
    }
}
