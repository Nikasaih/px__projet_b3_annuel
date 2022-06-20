package com.backend.storerate.dataobject.sqlrepository.grp;

import com.backend.storerate.dataobject.sqlentity.BillGrpSqlEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BillGrpSqlRepository extends CrudRepository<BillGrpSqlEntity, Long> {
    Set<BillGrpSqlEntity> findAllByCustomerId(Long customerId);
}
