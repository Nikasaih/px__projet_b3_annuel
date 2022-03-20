package spd.backend.dataobject.dto;

import lombok.Getter;
import lombok.Setter;
import spd.backend.dataobject.aentity.MaterialAbs;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MaterialDto extends MaterialAbs {
    private Long id;
    @NotNull
    private Iterable<Long> articlesId;
}
