package spd.backend.service.persistence;

import spd.backend.common.exception.EntityWithIdNotFoundExc;
import spd.backend.common.exception.IncorrectDtoForCreationExc;
import spd.backend.common.exception.IncorrectDtoForUpdateExc;
import spd.backend.dataobject.dto.CategoryDto;
import spd.backend.dataobject.sqlentity.ArticleSqlEntity;
import spd.backend.dataobject.sqlentity.CategorySqlEntity;
import spd.backend.dataobject.sqlrepository.ArticleSqlRepository;
import spd.backend.dataobject.sqlrepository.CategorySqlRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryPersistenceService {
    final ModelMapper mapper = new ModelMapper();
    @Autowired
    CategorySqlRepository categorySqlRepository;
    @Autowired
    ArticleSqlRepository articleSqlRepository;

    public Map<String, Object> createOne(final CategoryDto categoryToCreateInDb) throws IncorrectDtoForCreationExc {
        if (categoryToCreateInDb.getId() != null) {
            throw new IncorrectDtoForCreationExc();
        }

        return persistEntity(categoryToCreateInDb);
    }

    public Map<String, Object> updateOne(final CategoryDto categoryToUpdateInDb) throws IncorrectDtoForUpdateExc, EntityWithIdNotFoundExc {
        if (categoryToUpdateInDb.getId() == null) {
            throw new IncorrectDtoForUpdateExc();
        }
        Optional<CategorySqlEntity> categorySaved = categorySqlRepository.findById(categoryToUpdateInDb.getId());
        if (categorySaved.isEmpty()) {

            throw new EntityWithIdNotFoundExc(categoryToUpdateInDb.getId(), "Category");
        }

        return persistEntity(categoryToUpdateInDb);
    }

    private Map<String, Object> persistEntity(CategoryDto categoryToPersistInDb) {
        CategorySqlEntity categoryToSaveInSql;
        categoryToSaveInSql = mapper.map(categoryToPersistInDb, CategorySqlEntity.class);

        Iterable<ArticleSqlEntity> bookIterable = articleSqlRepository.findAllById(categoryToPersistInDb.getArticlesId());

        Map<String, Object> map = new HashMap<>();
        map.put("Sql", saveInSql(categoryToSaveInSql, bookIterable));
        return map;
    }


    private CategorySqlEntity saveInSql(CategorySqlEntity categoryToSave, Iterable<ArticleSqlEntity> articleSqlEntityIterable) {
        Set<ArticleSqlEntity> articleCollection = new HashSet<>();
        articleSqlEntityIterable.iterator().forEachRemaining(e -> articleCollection.add(e));

        categoryToSave.setArticles(articleCollection);
        return categorySqlRepository.save(categoryToSave);
    }
}
