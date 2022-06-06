package com.backend.storerate.dataobject.protobilling.sqlentity;

import com.backend.storerate.dataobject.protobilling.nosqlentity.BoxAbs;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class BillSqlEntity extends BoxAbs<BillElementSqlEntity> {
    private Float totalArticlePrice;
    private LocalDateTime payDate;

    @PrePersist
    public void prePersist() {
        payDate = LocalDateTime.now();
    }
}
