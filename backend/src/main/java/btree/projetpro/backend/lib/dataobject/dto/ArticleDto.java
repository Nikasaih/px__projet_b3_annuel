package btree.projetpro.backend.lib.dataobject.dto;

import btree.projetpro.backend.lib.dataobject.entity.CategoryEntity;
import btree.projetpro.backend.lib.dataobject.entity.ColorEntity;
import btree.projetpro.backend.lib.dataobject.entity.CommentEntity;
import btree.projetpro.backend.lib.dataobject.entity.MaterialEntity;
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
