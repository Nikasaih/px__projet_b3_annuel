package com.backend.storerate.protobilling.repository.el;

import com.backend.storerate.protobilling.repository.abs.BoxElementSqlRepositoryAbs;
import com.backend.storerate.protobilling.sqlentity.BillElementSqlEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface BillElementRepository extends BoxElementSqlRepositoryAbs<BillElementSqlEntity> {
}
