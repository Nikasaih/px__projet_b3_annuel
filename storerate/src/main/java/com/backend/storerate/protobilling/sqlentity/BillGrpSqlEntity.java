package com.backend.storerate.protobilling.sqlentity;

import com.backend.storerate.protobilling.sqlentity.embedable.BasketGrpEmb;
import com.backend.storerate.protobilling.sqlentity.nosqlentity.BoxGrpAbs;
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
