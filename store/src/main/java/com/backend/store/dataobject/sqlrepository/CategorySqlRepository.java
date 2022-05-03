package com.backend.store.dataobject.sqlrepository;

import com.backend.store.dataobject.sqlentity.CategorySqlEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorySqlRepository extends CrudRepository<CategorySqlEntity, Long> {
}
