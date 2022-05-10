package com.backend.storerate.service.delete;

import com.backend.storerate.common.exception.EntityWithIdNotFoundExc;
import com.backend.storerate.dataobject.sqlentity.CommentSqlEntity;
import com.backend.storerate.dataobject.sqlrepository.CommentSqlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentDeleteService {

    @Autowired
    CommentSqlRepository commentSqlRepository;

    public void deleteById(Long id) throws EntityWithIdNotFoundExc {
        Optional<CommentSqlEntity> commentSql = commentSqlRepository.findById(id);
        if (commentSql.isEmpty()) {
            throw new EntityWithIdNotFoundExc(id, "Category");
        }

        commentSqlRepository.delete(commentSql.get());
    }
}
