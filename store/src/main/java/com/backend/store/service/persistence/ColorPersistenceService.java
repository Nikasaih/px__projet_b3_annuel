package com.backend.store.service.persistence;

import com.backend.store.common.exception.EntityWithIdNotFoundExc;
import com.backend.store.common.exception.IncorrectDtoForCreationExc;
import com.backend.store.common.exception.IncorrectDtoForUpdateExc;
import com.backend.store.dataobject.dto.ColorDto;
import com.backend.store.dataobject.sqlentity.ArticleSqlEntity;
import com.backend.store.dataobject.sqlentity.ColorSqlEntity;
import com.backend.store.dataobject.sqlrepository.ArticleSqlRepository;
import com.backend.store.dataobject.sqlrepository.ColorSqlRepository;
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

    public Map<String, Object> createOne(final ColorDto colorToCreateInDb) throws IncorrectDtoForCreationExc {
        if (colorToCreateInDb.getId() != null) {
            throw new IncorrectDtoForCreationExc();
        }
        return persistEntity(colorToCreateInDb);
    }

    public Map<String, Object> updateOne(final ColorDto colorToUpdateInDb) throws IncorrectDtoForUpdateExc, EntityWithIdNotFoundExc {
        if (colorToUpdateInDb.getId() == null) {
            throw new IncorrectDtoForUpdateExc();
        }
        Optional<ColorSqlEntity> colorSaved = colorSqlRepository.findById(colorToUpdateInDb.getId());
        if (colorSaved.isEmpty()) {

            throw new EntityWithIdNotFoundExc(colorToUpdateInDb.getId(), "Color");
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
