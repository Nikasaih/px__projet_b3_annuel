package com.backend.storerate.service.persistence;

import com.backend.storerate.common.exception.EntityRelatedNotFoundExc;
import com.backend.storerate.common.exception.EntityWithIdNotFoundExc;
import com.backend.storerate.common.exception.IncorrectDtoForCreationExc;
import com.backend.storerate.common.exception.IncorrectDtoForUpdateExc;
import com.backend.storerate.dataobject.dto.CommentDto;
import com.backend.storerate.dataobject.sqlentity.CommentSqlEntity;
import com.backend.storerate.dataobject.sqlrepository.CommentSqlRepository;
import com.backend.storerate.service.redirection.StoreArticleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentPersistenceService {
    final ModelMapper mapper = new ModelMapper();

    @Autowired
    CommentSqlRepository commentSqlRepository;
    @Autowired
    StoreArticleService storeArticleService;

    public CommentSqlEntity createOne(final CommentDto commentToCreateInDb) throws IncorrectDtoForCreationExc, EntityRelatedNotFoundExc {
        if (commentToCreateInDb.getId() != null) {
            throw new IncorrectDtoForCreationExc();
        }
        return persistEntity(commentToCreateInDb);
    }

    public CommentSqlEntity updateOne(final CommentDto commentToUpdateInDb) throws IncorrectDtoForUpdateExc, EntityWithIdNotFoundExc, EntityRelatedNotFoundExc {
        if (commentToUpdateInDb.getId() == null) {
            throw new IncorrectDtoForUpdateExc();
        }
        Optional<CommentSqlEntity> commentSaved = commentSqlRepository.findById(commentToUpdateInDb.getId());
        if (commentSaved.isEmpty()) {

            throw new EntityWithIdNotFoundExc(commentToUpdateInDb.getId(), "Comment");
        }

        return persistEntity(commentToUpdateInDb);
    }

    private CommentSqlEntity persistEntity(CommentDto commentToPersistInDb) throws EntityRelatedNotFoundExc {
        CommentSqlEntity commentToSaveInSql;
        commentToSaveInSql = mapper.map(commentToPersistInDb, CommentSqlEntity.class);

        CommentSqlEntity commentSqlEntity = saveInSql(commentToSaveInSql);
        storeArticleService.UpdateArticleGradeAndCustomerNumberByArticleId(commentToPersistInDb.getArticleId());
        return commentSqlEntity;
    }


    private CommentSqlEntity saveInSql(CommentSqlEntity commentToSave) {
        return commentSqlRepository.save(commentToSave);
    }

}
