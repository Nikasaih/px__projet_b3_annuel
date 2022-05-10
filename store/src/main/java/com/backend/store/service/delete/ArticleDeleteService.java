package com.backend.store.service.delete;

import com.backend.store.common.exception.EntityWithIdNotFoundExc;
import com.backend.store.dataobject.sqlentity.ArticleSqlEntity;
import com.backend.store.dataobject.sqlentity.CategorySqlEntity;
import com.backend.store.dataobject.sqlentity.ColorSqlEntity;
import com.backend.store.dataobject.sqlentity.MaterialSqlEntity;
import com.backend.store.dataobject.sqlrepository.ArticleSqlRepository;
import com.backend.store.dataobject.sqlrepository.CategorySqlRepository;
import com.backend.store.dataobject.sqlrepository.ColorSqlRepository;
import com.backend.store.dataobject.sqlrepository.MaterialSqlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ArticleDeleteService {
    @Autowired
    ArticleSqlRepository articleSqlRepository;
    @Autowired
    CategorySqlRepository categorySqlRepository;
    @Autowired
    ColorSqlRepository colorSqlRepository;
    @Autowired
    MaterialSqlRepository materialSqlRepository;


    public void deleteById(Long id) throws EntityWithIdNotFoundExc {
        Optional<ArticleSqlEntity> articleSql = articleSqlRepository.findById(id);
        if (articleSql.isEmpty()) {
            throw new EntityWithIdNotFoundExc(id, "Article");
        }

        delete(articleSql.get());
        articleSqlRepository.delete(articleSql.get());
    }

    public void delete(ArticleSqlEntity articles) {
        Set<CategorySqlEntity> categories = articles.getCategories();
        Set<ColorSqlEntity> colors = articles.getColors();
        Set<MaterialSqlEntity> materials = articles.getMaterials();

        categories.forEach(e -> categorySqlRepository.save(e.removeArticle(articles)));
        colors.forEach(e -> colorSqlRepository.save(e.removeArticle(articles)));
        materials.forEach(e -> materialSqlRepository.save(e.removeArticle(articles)));

    }
}
