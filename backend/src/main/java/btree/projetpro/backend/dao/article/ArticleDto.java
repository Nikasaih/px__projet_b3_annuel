package btree.projetpro.backend.dao.article;

import btree.projetpro.backend.dao.categorie.CategoryEntity;
import btree.projetpro.backend.dao.color.ColorEntity;
import btree.projetpro.backend.dao.comment.CommentEntity;
import btree.projetpro.backend.dao.material.MaterialEntity;
import btree.projetpro.backend.dao.util.persistenceservice.Dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ArticleDto extends Dto {
    private Long id;
    private String name;
    private Long price;
    private String description;
    private String size;
    private Long stocks;
    private Long customerNumber;
    private String imagePath;
    private List<CategoryEntity> categories;
    private List<MaterialEntity> materials;
    private List<ColorEntity> colors;
    private List<CommentEntity> comment;

}
