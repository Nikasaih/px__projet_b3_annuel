package spd.backend.service.delete;

import spd.backend.common.exception.EntityWithIdNotFoundExc;
import spd.backend.dataobject.sqlentity.ArticleSqlEntity;
import spd.backend.dataobject.sqlentity.CategorySqlEntity;
import spd.backend.dataobject.sqlrepository.ArticleSqlRepository;
import spd.backend.dataobject.sqlrepository.CategorySqlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class CategoryDeleteService {
    @Autowired
    ArticleSqlRepository articleSqlRepository;
    @Autowired
    CategorySqlRepository categorySqlRepository;

    public void deleteById(Long id) throws EntityWithIdNotFoundExc {
        Optional<CategorySqlEntity> categorySql = categorySqlRepository.findById(id);
        if (categorySql.isEmpty()) {
            throw new EntityWithIdNotFoundExc(id, "Category");
        }

        delete(categorySql.get());
        categorySqlRepository.delete(categorySql.get());
    }


    public void delete(CategorySqlEntity categorySql) {
        Set<ArticleSqlEntity> articles = categorySql.getArticles();
        articles.forEach(e -> articleSqlRepository.save(e.removeCategory(categorySql)));
    }
}
