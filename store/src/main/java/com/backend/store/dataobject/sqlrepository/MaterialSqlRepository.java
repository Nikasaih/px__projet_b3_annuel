package com.backend.store.dataobject.sqlrepository;

import com.backend.store.dataobject.sqlentity.MaterialSqlEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialSqlRepository extends CrudRepository<MaterialSqlEntity, Long> {
}
