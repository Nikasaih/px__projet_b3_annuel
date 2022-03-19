package spd.backend.dataobject.dto;

import lombok.Getter;
import lombok.Setter;
import spd.backend.dataobject.aentity.ColorAbs;

@Getter
@Setter
public class ColorDto extends ColorAbs {
    private Long id;
    private Iterable<Long> articlesId;
}
