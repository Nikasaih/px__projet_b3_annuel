package com.backend.store.service.delete;

import com.backend.store.common.exception.EntityWithIdNotFoundExc;
import com.backend.store.dataobject.sqlentity.ArticleSqlEntity;
import com.backend.store.dataobject.sqlentity.CategorySqlEntity;
import com.backend.store.dataobject.sqlrepository.ArticleSqlRepository;
import com.backend.store.dataobject.sqlrepository.CategorySqlRepository;
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
