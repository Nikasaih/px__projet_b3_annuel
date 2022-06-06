package com.backend.storerate.protobilling.repository.grp;

import com.backend.storerate.protobilling.repository.abs.BoxGrpSqlRepositoryAbs;
import com.backend.storerate.protobilling.sqlentity.BasketGrpSqlEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketGrpSqlRepository extends BoxGrpSqlRepositoryAbs<BasketGrpSqlEntity> {
}
