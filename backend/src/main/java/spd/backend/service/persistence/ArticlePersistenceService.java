package spd.backend.service.persistence;

import spd.backend.common.exception.EntityWithIdNotFound;
import spd.backend.common.exception.IncorrectDtoForCreation;
import spd.backend.common.exception.IncorrectDtoForUpdate;
import spd.backend.dataobject.dto.ArticleDto;
import spd.backend.dataobject.sqlentity.*;
import spd.backend.dataobject.sqlrepository.*;
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
    CommentSqlRepository commentSqlRepository;

    public Map<String, Object> createOne(final ArticleDto articleToCreateInDb) throws IncorrectDtoForCreation {
        if (articleToCreateInDb.getId() != null) {
            throw new IncorrectDtoForCreation();
        }
        return persistEntity(articleToCreateInDb);
    }

    public Map<String, Object> updateOne(final ArticleDto articleToUpdateInDb) throws IncorrectDtoForUpdate, EntityWithIdNotFound {
        if (articleToUpdateInDb.getId() == null) {
            throw new IncorrectDtoForUpdate();
        }
        Optional<ArticleSqlEntity> articleSaved = articleSqlRepository.findById(articleToUpdateInDb.getId());
        if (articleSaved.isEmpty()) {
            throw new EntityWithIdNotFound(articleToUpdateInDb.getId(), "Article");
        }

        return persistEntity(articleToUpdateInDb);
    }


    private Map<String, Object> persistEntity(ArticleDto articleToPersistInDb) {
        ArticleSqlEntity articleToSaveInSql;
        articleToSaveInSql = mapper.map(articleToPersistInDb, ArticleSqlEntity.class);

        Iterable<ColorSqlEntity> colors = colorSqlRepository.findAllById(articleToPersistInDb.getColorsId());
        Iterable<CategorySqlEntity> categories = categorySqlRepository.findAllById(articleToPersistInDb.getCategoriesId());
        Iterable<MaterialSqlEntity> materials = materialSqlRepository.findAllById(articleToPersistInDb.getMaterialsId());
        Iterable<CommentSqlEntity> comments = commentSqlRepository.findAllById(articleToPersistInDb.getCommentsId());


        Map<String, Object> map = new HashMap<>();
        map.put("Sql", saveInSql(articleToSaveInSql, colors, categories, materials, comments));
        return map;
    }

    private ArticleSqlEntity saveInSql(ArticleSqlEntity articleToSave,
                                       Iterable<ColorSqlEntity> colorSqlEntityIterable,
                                       Iterable<CategorySqlEntity> categorySqlEntityIterable,
                                       Iterable<MaterialSqlEntity> materialSqlEntityIterable,
                                       Iterable<CommentSqlEntity> commentSqlEntityIterable
    ) {

        articleToSave.setColors(Sets.newHashSet(colorSqlEntityIterable));
        articleToSave.setCategories(Sets.newHashSet(categorySqlEntityIterable));
        articleToSave.setMaterials(Sets.newHashSet(materialSqlEntityIterable));
        articleToSave.setComments(Sets.newHashSet(commentSqlEntityIterable));

        return articleSqlRepository.save(articleToSave);
    }
}
