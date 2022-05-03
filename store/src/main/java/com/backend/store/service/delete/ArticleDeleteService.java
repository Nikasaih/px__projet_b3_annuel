package com.backend.store.service.delete;

import com.backend.store.common.exception.EntityWithIdNotFoundExc;
import com.backend.store.dataobject.elkrepository.ArticleElkRepository;
import com.backend.store.dataobject.sqlentity.*;
import com.backend.store.dataobject.sqlrepository.*;
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
    @Autowired
    ArticleElkRepository articleElkRepository;

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

        articleElkRepository.deleteById(articles.getId());
    }
}
