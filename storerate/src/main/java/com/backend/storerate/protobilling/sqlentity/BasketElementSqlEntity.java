package com.backend.storerate.protobilling.sqlentity;

import com.backend.storerate.protobilling.sqlentity.embedable.BasketElEmb;
import com.backend.storerate.protobilling.sqlentity.nosqlentity.BoxElementAbs;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class BasketElementSqlEntity extends BoxElementAbs<BasketGrpSqlEntity> {
    @Embedded
    private BasketElEmb basketEmb;
}
