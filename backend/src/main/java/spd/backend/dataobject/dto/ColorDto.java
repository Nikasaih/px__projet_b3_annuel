package spd.backend.dataobject.dto;

import spd.backend.dataobject.aentity.AColor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColorDto extends AColor {
    private Long id;
    private Iterable<Long> articlesId;
}
