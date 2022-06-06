package com.backend.storerate.protobilling.repository.grp;

import com.backend.storerate.protobilling.sqlentity.BillGrpSqlEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BillGrpSqlRepository extends CrudRepository<BillGrpSqlEntity, Long> {
    Set<BillGrpSqlEntity> findAllByCustomerId(Long customerId);
}
