package com.backend.store.service.persistence;

import com.backend.store.common.exception.EntityWithIdNotFoundExc;
import com.backend.store.common.exception.IncorrectDtoForCreationExc;
import com.backend.store.common.exception.IncorrectDtoForUpdateExc;
import com.backend.store.dataobject.dto.MaterialDto;
import com.backend.store.dataobject.sqlentity.ArticleSqlEntity;
import com.backend.store.dataobject.sqlentity.MaterialSqlEntity;
import com.backend.store.dataobject.sqlrepository.ArticleSqlRepository;
import com.backend.store.dataobject.sqlrepository.MaterialSqlRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MaterialPersistenceService {
    final ModelMapper mapper = new ModelMapper();
    //Bean
    @Autowired
    MaterialSqlRepository materialSqlRepository;
    @Autowired
    ArticleSqlRepository articleSqlRepository;

    public Map<String, Object> createOne(final MaterialDto materialToCreateInDb) throws IncorrectDtoForCreationExc {
        if (materialToCreateInDb.getId() != null) {
            throw new IncorrectDtoForCreationExc();
        }
        return persistEntity(materialToCreateInDb);
    }

    public Map<String, Object> updateOne(final MaterialDto materialToUpdateInDb) throws IncorrectDtoForUpdateExc, EntityWithIdNotFoundExc {
        if (materialToUpdateInDb.getId() == null) {
            throw new IncorrectDtoForUpdateExc();
        }
        Optional<MaterialSqlEntity> materialSaved = materialSqlRepository.findById(materialToUpdateInDb.getId());
        if (materialSaved.isEmpty()) {

            throw new EntityWithIdNotFoundExc(materialToUpdateInDb.getId(), "Material");
        }

        return persistEntity(materialToUpdateInDb);
    }

    private Map<String, Object> persistEntity(MaterialDto materialToPersistInDb) {
        MaterialSqlEntity materialToSaveInSql;
        materialToSaveInSql = mapper.map(materialToPersistInDb, MaterialSqlEntity.class);

        Iterable<ArticleSqlEntity> bookIterable = articleSqlRepository.findAllById(materialToPersistInDb.getArticlesId());

        Map<String, Object> map = new HashMap<>();
        map.put("Sql", saveInSql(materialToSaveInSql, bookIterable));
        return map;
    }


    private MaterialSqlEntity saveInSql(MaterialSqlEntity materialToSave, Iterable<ArticleSqlEntity> articleSqlEntityIterable) {
        Set<ArticleSqlEntity> articleCollection = new HashSet<>();
        articleSqlEntityIterable.iterator().forEachRemaining(e -> articleCollection.add(e));

        materialToSave.setArticles(articleCollection);
        return materialSqlRepository.save(materialToSave);
    }
}
