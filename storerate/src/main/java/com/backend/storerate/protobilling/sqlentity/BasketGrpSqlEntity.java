package com.backend.storerate.protobilling.sqlentity;

import com.backend.storerate.protobilling.sqlentity.embedable.BasketGrpEmb;
import com.backend.storerate.protobilling.sqlentity.nosqlentity.BoxGrpAbs;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@ToString
public class BasketGrpSqlEntity extends BoxGrpAbs<BasketElementSqlEntity> {
    @Embedded
    BasketGrpEmb basketGrpEmb;
}