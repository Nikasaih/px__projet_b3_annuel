package com.backend.storerate.dataobject.sqlentity;

import com.backend.storerate.dataobject.embedable.BasketGrpEmb;
import com.backend.storerate.dataobject.nosqlentity.BoxGrpAbs;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class BillGrpSqlEntity extends BoxGrpAbs<BillElementSqlEntity> {
    @Embedded
    BasketGrpEmb basketGrpEmb;

    private LocalDateTime payDate;

    @PrePersist
    public void prePersist() {
        payDate = LocalDateTime.now();
    }
}
