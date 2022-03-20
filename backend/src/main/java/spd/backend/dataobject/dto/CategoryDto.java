package spd.backend.dataobject.dto;

import lombok.Getter;
import lombok.Setter;
import spd.backend.dataobject.aentity.CategoryAbs;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CategoryDto extends CategoryAbs {
    private Long id;
    @NotNull
    private Iterable<Long> articlesId;
}
