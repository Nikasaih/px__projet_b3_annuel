package spd.backend.dataobject.dto;

import lombok.Getter;
import lombok.Setter;
import spd.backend.dataobject.aentity.ArticleAbs;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
public class ArticleDto extends ArticleAbs {

    private Long id;
    @NotNull
    private Set<Long> commentsId;
    @NotNull
    private Set<Long> categoriesId;
    @NotNull
    private Set<Long> colorsId;
    @NotNull
    private Set<Long> materialsId;
}
