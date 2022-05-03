package com.backend.store.service.delete;

import com.backend.store.common.exception.EntityWithIdNotFoundExc;
import com.backend.store.dataobject.sqlentity.ArticleSqlEntity;
import com.backend.store.dataobject.sqlentity.ColorSqlEntity;
import com.backend.store.dataobject.sqlrepository.ArticleSqlRepository;
import com.backend.store.dataobject.sqlrepository.ColorSqlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ColorDeleteService {
    @Autowired
    ArticleSqlRepository articleSqlRepository;
    @Autowired
    ColorSqlRepository colorSqlRepository;

    public void deleteById(Long id) throws EntityWithIdNotFoundExc {
        Optional<ColorSqlEntity> colorSql = colorSqlRepository.findById(id);
        if (colorSql.isEmpty()) {
            throw new EntityWithIdNotFoundExc(id, "Category");
        }

        delete(colorSql.get());
        colorSqlRepository.delete(colorSql.get());
    }


    public void delete(ColorSqlEntity categorySql) {
        Set<ArticleSqlEntity> articles = categorySql.getArticles();
        articles.forEach(e -> articleSqlRepository.save(e.removeColor(categorySql)));
    }
}
