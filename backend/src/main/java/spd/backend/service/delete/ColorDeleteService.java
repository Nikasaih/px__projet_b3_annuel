package spd.backend.service.delete;

import spd.backend.common.exception.EntityWithIdNotFoundExc;
import spd.backend.dataobject.sqlentity.ArticleSqlEntity;
import spd.backend.dataobject.sqlentity.ColorSqlEntity;
import spd.backend.dataobject.sqlrepository.ArticleSqlRepository;
import spd.backend.dataobject.sqlrepository.ColorSqlRepository;
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
