package spd.backend.service.persistence;

import spd.backend.common.exception.EntityWithIdNotFound;
import spd.backend.common.exception.IncorrectDtoForCreation;
import spd.backend.common.exception.IncorrectDtoForUpdate;
import spd.backend.dataobject.dto.CommentDto;
import spd.backend.dataobject.sqlentity.ArticleSqlEntity;
import spd.backend.dataobject.sqlentity.CommentSqlEntity;
import spd.backend.dataobject.sqlrepository.ArticleSqlRepository;
import spd.backend.dataobject.sqlrepository.CommentSqlRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CommentPersistenceService {
    final ModelMapper mapper = new ModelMapper();
    //Bean
    @Autowired
    CommentSqlRepository commentSqlRepository;
    @Autowired
    ArticleSqlRepository articleSqlRepository;

    public Map<String, Object> createOne(final CommentDto commentToCreateInDb) throws IncorrectDtoForCreation, EntityWithIdNotFound {
        if (commentToCreateInDb.getId() != null) {
            throw new IncorrectDtoForCreation();
        }
        return persistEntity(commentToCreateInDb);
    }

    public Map<String, Object> updateOne(final CommentDto commentToUpdateInDb) throws IncorrectDtoForUpdate, EntityWithIdNotFound {
        if (commentToUpdateInDb.getId() == null) {
            throw new IncorrectDtoForUpdate();
        }
        Optional<CommentSqlEntity> commentSaved = commentSqlRepository.findById(commentToUpdateInDb.getId());
        if (commentSaved.isEmpty()) {

            throw new EntityWithIdNotFound(commentToUpdateInDb.getId(), "Comment");
        }

        return persistEntity(commentToUpdateInDb);
    }

    private Map<String, Object> persistEntity(CommentDto commentToPersistInDb) throws EntityWithIdNotFound {
        CommentSqlEntity commentToSaveInSql;
        commentToSaveInSql = mapper.map(commentToPersistInDb, CommentSqlEntity.class);

        Optional<ArticleSqlEntity> articleRelated = articleSqlRepository.findById(commentToPersistInDb.getArticlesId());
        if (articleRelated.isEmpty()) {
            throw new EntityWithIdNotFound(commentToPersistInDb.getArticlesId(), "Article");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("Sql", saveInSql(commentToSaveInSql, articleRelated.get()));
        return map;
    }


    private CommentSqlEntity saveInSql(CommentSqlEntity commentToSave, ArticleSqlEntity articleRelated) {
        commentToSave.setArticles(articleRelated);
        return commentSqlRepository.save(commentToSave);
    }
}
