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
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.util.set.Sets;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class ArticlePersistenceService {

    //Todo persist with httprequest elk
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

        articleToSave.setColors(Sets.newHashSet(colorSqlEntityIterable));
        articleToSave.setCategories(Sets.newHashSet(categorySqlEntityIterable));
        articleToSave.setMaterials(Sets.newHashSet(materialSqlEntityIterable));
        log.info("in saveInSql");
        return articleSqlRepository.save(articleToSave);
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
