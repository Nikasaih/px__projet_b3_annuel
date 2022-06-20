package com.backend.storerate.dataobject.embedable;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class BasketGrpEmb {
    private Float totalArticlePrice;

}
