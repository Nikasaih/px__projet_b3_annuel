package spd.backend.dataobject.dto;

import spd.backend.dataobject.aentity.AMaterial;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaterialDto extends AMaterial {
    private Long id;
    private Iterable<Long> articlesId;
}
