package spd.backend.dataobject.aentity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AArticle {
    private String name;
    private Long price;
    private String description;
    private String size;
    private Long stocks;
    private Long customerNumber;
    private String imagePath;
}
