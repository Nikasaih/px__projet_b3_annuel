package spd.backend.dataobject.dto;

import lombok.Getter;
import lombok.Setter;
import spd.backend.dataobject.aentity.CategoryAbs;

@Getter
@Setter
public class CategoryDto extends CategoryAbs {
    private Long id;
    private Iterable<Long> articlesId;
}
