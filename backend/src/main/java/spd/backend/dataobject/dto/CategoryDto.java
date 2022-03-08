package spd.backend.dataobject.dto;

import spd.backend.dataobject.aentity.ACategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto extends ACategory {
    private Long id;
    private Iterable<Long> articlesId;
}
