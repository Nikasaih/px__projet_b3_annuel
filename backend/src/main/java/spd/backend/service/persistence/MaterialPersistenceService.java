package spd.backend.service.persistence;

import spd.backend.common.exception.EntityWithIdNotFound;
import spd.backend.common.exception.IncorrectDtoForCreation;
import spd.backend.common.exception.IncorrectDtoForUpdate;
import spd.backend.dataobject.dto.MaterialDto;
import spd.backend.dataobject.sqlentity.ArticleSqlEntity;
import spd.backend.dataobject.sqlentity.MaterialSqlEntity;
import spd.backend.dataobject.sqlrepository.ArticleSqlRepository;
import spd.backend.dataobject.sqlrepository.MaterialSqlRepository;
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

    public Map<String, Object> createOne(final MaterialDto materialToCreateInDb) throws IncorrectDtoForCreation {
        if (materialToCreateInDb.getId() != null) {
            throw new IncorrectDtoForCreation();
        }
        return persistEntity(materialToCreateInDb);
    }

    public Map<String, Object> updateOne(final MaterialDto materialToUpdateInDb) throws IncorrectDtoForUpdate, EntityWithIdNotFound {
        if (materialToUpdateInDb.getId() == null) {
            throw new IncorrectDtoForUpdate();
        }
        Optional<MaterialSqlEntity> materialSaved = materialSqlRepository.findById(materialToUpdateInDb.getId());
        if (materialSaved.isEmpty()) {

            throw new EntityWithIdNotFound(materialToUpdateInDb.getId(), "Material");
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
