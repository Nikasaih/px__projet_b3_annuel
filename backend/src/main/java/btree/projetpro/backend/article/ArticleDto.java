package btree.projetpro.backend.article;

import btree.projetpro.backend.categorie.CategoryEntity;
import btree.projetpro.backend.color.ColorEntity;
import btree.projetpro.backend.comment.CommentEntity;
import btree.projetpro.backend.material.MaterialEntity;
import btree.projetpro.backend.util.Dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ArticleDto implements Dto {
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
