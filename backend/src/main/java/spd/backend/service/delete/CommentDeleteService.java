package spd.backend.service.delete;

import spd.backend.common.exception.EntityWithIdNotFound;
import spd.backend.dataobject.sqlentity.ArticleSqlEntity;
import spd.backend.dataobject.sqlentity.CommentSqlEntity;
import spd.backend.dataobject.sqlrepository.ArticleSqlRepository;
import spd.backend.dataobject.sqlrepository.CommentSqlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentDeleteService {
    @Autowired
    ArticleSqlRepository articleSqlRepository;
    @Autowired
    CommentSqlRepository commentSqlRepository;

    public void deleteById(Long id) throws EntityWithIdNotFound {
        Optional<CommentSqlEntity> commentSql = commentSqlRepository.findById(id);
        if (commentSql.isEmpty()) {
            throw new EntityWithIdNotFound(id, "Category");
        }

        delete(commentSql.get());
        commentSqlRepository.delete(commentSql.get());
    }


    public void delete(CommentSqlEntity commentSql) {
        ArticleSqlEntity article = commentSql.getArticles();
        articleSqlRepository.save(article.removeComment(commentSql));
    }
}
