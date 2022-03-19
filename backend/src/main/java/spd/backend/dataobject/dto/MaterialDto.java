package spd.backend.dataobject.dto;

import lombok.Getter;
import lombok.Setter;
import spd.backend.dataobject.aentity.MaterialAbs;

@Getter
@Setter
public class MaterialDto extends MaterialAbs {
    private Long id;
    private Iterable<Long> articlesId;
}
