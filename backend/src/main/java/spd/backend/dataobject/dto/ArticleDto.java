package spd.backend.dataobject.dto;

import spd.backend.dataobject.aentity.AArticle;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ArticleDto extends AArticle {
    private Long id;
    private Set<Long> categoriesId;
    private Set<Long> colorsId;
    private Set<Long> materialsId;
    private Set<Long> commentsId;
}
