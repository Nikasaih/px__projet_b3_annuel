package com.backend.storerate.dataobject.sqlentity;

import com.backend.storerate.dataobject.embedable.BasketElEmb;
import com.backend.storerate.dataobject.nosqlentity.BoxElementAbs;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class BillElementSqlEntity extends BoxElementAbs<BillGrpSqlEntity> {
    @Embedded
    private BasketElEmb basketEmb;
}
