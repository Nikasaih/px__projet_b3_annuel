package spd.backend.service.delete;

import spd.backend.common.exception.EntityWithIdNotFound;
import spd.backend.dataobject.sqlentity.ArticleSqlEntity;
import spd.backend.dataobject.sqlentity.MaterialSqlEntity;
import spd.backend.dataobject.sqlrepository.ArticleSqlRepository;
import spd.backend.dataobject.sqlrepository.MaterialSqlRepository;
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

    public void deleteById(Long id) throws EntityWithIdNotFound {
        Optional<MaterialSqlEntity> materialSql = materialSqlRepository.findById(id);
        if (materialSql.isEmpty()) {
            throw new EntityWithIdNotFound(id, "Category");
        }

        delete(materialSql.get());
        materialSqlRepository.delete(materialSql.get());
    }


    public void delete(MaterialSqlEntity materialSql) {
        Set<ArticleSqlEntity> articles = materialSql.getArticles();
        articles.forEach(e -> articleSqlRepository.save(e.removeMaterial(materialSql)));
    }
}
