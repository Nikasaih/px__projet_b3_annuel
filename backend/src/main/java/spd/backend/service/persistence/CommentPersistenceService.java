package spd.backend.service.persistence;

import spd.backend.common.exception.EntityWithIdNotFoundExc;
import spd.backend.common.exception.IncorrectDtoForCreationExc;
import spd.backend.common.exception.IncorrectDtoForUpdateExc;
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

    public Map<String, Object> createOne(final CommentDto commentToCreateInDb) throws IncorrectDtoForCreationExc, EntityWithIdNotFoundExc {
        if (commentToCreateInDb.getId() != null) {
            throw new IncorrectDtoForCreationExc();
        }
        return persistEntity(commentToCreateInDb);
    }

    public Map<String, Object> updateOne(final CommentDto commentToUpdateInDb) throws IncorrectDtoForUpdateExc, EntityWithIdNotFoundExc {
        if (commentToUpdateInDb.getId() == null) {
            throw new IncorrectDtoForUpdateExc();
        }
        Optional<CommentSqlEntity> commentSaved = commentSqlRepository.findById(commentToUpdateInDb.getId());
        if (commentSaved.isEmpty()) {

            throw new EntityWithIdNotFoundExc(commentToUpdateInDb.getId(), "Comment");
        }

        return persistEntity(commentToUpdateInDb);
    }

    private Map<String, Object> persistEntity(CommentDto commentToPersistInDb) throws EntityWithIdNotFoundExc {
        CommentSqlEntity commentToSaveInSql;
        commentToSaveInSql = mapper.map(commentToPersistInDb, CommentSqlEntity.class);

        Optional<ArticleSqlEntity> articleRelated = articleSqlRepository.findById(commentToPersistInDb.getArticlesId());
        if (articleRelated.isEmpty()) {
            throw new EntityWithIdNotFoundExc(commentToPersistInDb.getArticlesId(), "Article");
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
