package com.backend.store.service.persistence;

import com.backend.store.common.exception.EntityWithIdNotFoundExc;
import com.backend.store.common.exception.IncorrectDtoForCreationExc;
import com.backend.store.common.exception.IncorrectDtoForUpdateExc;
import com.backend.store.dataobject.dto.ArticleDto;
import com.backend.store.dataobject.request.ChangeArticleGradeRequest;
import com.backend.store.dataobject.sqlentity.ArticleSqlEntity;
import com.backend.store.dataobject.sqlentity.CategorySqlEntity;
import com.backend.store.dataobject.sqlentity.ColorSqlEntity;
import com.backend.store.dataobject.sqlentity.MaterialSqlEntity;
import com.backend.store.dataobject.sqlrepository.ArticleSqlRepository;
import com.backend.store.dataobject.sqlrepository.CategorySqlRepository;
import com.backend.store.dataobject.sqlrepository.ColorSqlRepository;
import com.backend.store.dataobject.sqlrepository.MaterialSqlRepository;
import com.backend.store.service.ElkServices;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ArticlePersistenceService {

    final ModelMapper mapper = new ModelMapper();
    //Bean
    @Autowired
    ArticleSqlRepository articleSqlRepository;
    @Autowired
    ColorSqlRepository colorSqlRepository;
    @Autowired
    CategorySqlRepository categorySqlRepository;
    @Autowired
    MaterialSqlRepository materialSqlRepository;
    @Autowired
    ElkServices elkServices;

    public Map<String, Object> createOne(final ArticleDto articleToCreateInDb) throws IncorrectDtoForCreationExc {
        if (articleToCreateInDb.getId() != null) {
            throw new IncorrectDtoForCreationExc();
        }
        return persistEntity(articleToCreateInDb);
    }

    public Map<String, Object> updateOne(final ArticleDto articleToUpdateInDb) throws IncorrectDtoForUpdateExc, EntityWithIdNotFoundExc {
        if (articleToUpdateInDb.getId() == null) {
            throw new IncorrectDtoForUpdateExc();
        }
        Optional<ArticleSqlEntity> articleSaved = articleSqlRepository.findById(articleToUpdateInDb.getId());
        if (articleSaved.isEmpty()) {
            throw new EntityWithIdNotFoundExc(articleToUpdateInDb.getId(), "Article");
        }

        return persistEntity(articleToUpdateInDb);
    }


    private Map<String, Object> persistEntity(ArticleDto articleToPersistInDb) {
        ArticleSqlEntity articleToSaveInSql = mapper.map(articleToPersistInDb, ArticleSqlEntity.class);

        Iterable<ColorSqlEntity> colors = colorSqlRepository.findAllById(articleToPersistInDb.getColorsId());
        Iterable<CategorySqlEntity> categories = categorySqlRepository.findAllById(articleToPersistInDb.getCategoriesId());
        Iterable<MaterialSqlEntity> materials = materialSqlRepository.findAllById(articleToPersistInDb.getMaterialsId());

        ArticleSqlEntity entity = saveInSql(articleToSaveInSql, colors, categories, materials);
        Map<String, Object> map = new HashMap<>();
        map.put("Sql", entity);
        return map;
    }

    private ArticleSqlEntity saveInSql(ArticleSqlEntity articleToSave,
                                       Iterable<ColorSqlEntity> colorSqlEntityIterable,
                                       Iterable<CategorySqlEntity> categorySqlEntityIterable,
                                       Iterable<MaterialSqlEntity> materialSqlEntityIterable
    ) {
        Set<ColorSqlEntity> colorSet = new HashSet<ColorSqlEntity>();
        colorSqlEntityIterable.iterator().forEachRemaining((e -> colorSet.add(e)));
        Set<CategorySqlEntity> categorySet = new HashSet<>();
        categorySqlEntityIterable.iterator().forEachRemaining((e -> categorySet.add(e)));
        Set<MaterialSqlEntity> materialSet = new HashSet<>();
        materialSqlEntityIterable.iterator().forEachRemaining((e -> materialSet.add(e)));

        articleToSave.setColors(colorSet);
        articleToSave.setCategories(categorySet);
        articleToSave.setMaterials(materialSet);

        ArticleSqlEntity articleSql = articleSqlRepository.save(articleToSave);
        elkServices.createFromSql(articleSql);
        return articleSql;
    }

    public ArticleSqlEntity changeGradeAndCustomerNumber(ChangeArticleGradeRequest changeArticleGradeRequest) throws EntityWithIdNotFoundExc {
        Optional<ArticleSqlEntity> articleSql = articleSqlRepository.findById(changeArticleGradeRequest.getArticleId());
        if (articleSql.isEmpty()) {
            throw new EntityWithIdNotFoundExc(changeArticleGradeRequest.getArticleId(), "Article");
        }

        articleSql.get().setGrade(changeArticleGradeRequest.getNewGrade());
        articleSql.get().setCustomerNumber(changeArticleGradeRequest.getCustomerNumber());
        return articleSqlRepository.save(articleSql.get());
    }
}
