package com.backend.store.service.delete;

import com.backend.store.common.exception.EntityWithIdNotFoundExc;
import com.backend.store.dataobject.sqlentity.ArticleSqlEntity;
import com.backend.store.dataobject.sqlentity.MaterialSqlEntity;
import com.backend.store.dataobject.sqlrepository.ArticleSqlRepository;
import com.backend.store.dataobject.sqlrepository.MaterialSqlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class MaterialDeleteService {
    @Autowired
    ArticleSqlRepository articleSqlRepository;
    @Autowired
    MaterialSqlRepository materialSqlRepository;

    public void deleteById(Long id) throws EntityWithIdNotFoundExc {
        Optional<MaterialSqlEntity> materialSql = materialSqlRepository.findById(id);
        if (materialSql.isEmpty()) {
            throw new EntityWithIdNotFoundExc(id, "Category");
        }

        delete(materialSql.get());
        materialSqlRepository.delete(materialSql.get());
    }


    public void delete(MaterialSqlEntity materialSql) {
        Set<ArticleSqlEntity> articles = materialSql.getArticles();
        articles.forEach(e -> articleSqlRepository.save(e.removeMaterial(materialSql)));
    }
}
