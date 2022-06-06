package com.backend.storerate.dataobject.protobilling.repository;

import com.backend.storerate.dataobject.protobilling.repository.abs.BoxSqlRepositoryAbs;
import com.backend.storerate.dataobject.protobilling.sqlentity.BillSqlEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface BillSqlRepository extends BoxSqlRepositoryAbs<BillSqlEntity> {
    Iterable<BillSqlEntity> findAllByCustomerId(Long customerId);
}
