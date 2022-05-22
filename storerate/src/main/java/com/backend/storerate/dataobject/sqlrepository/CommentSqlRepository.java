package com.backend.storerate.dataobject.sqlrepository;

import com.backend.storerate.dataobject.sqlentity.CommentSqlEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentSqlRepository extends CrudRepository<CommentSqlEntity, Long> {
    List<CommentSqlEntity> findByArticleId(Long articleId);
}
