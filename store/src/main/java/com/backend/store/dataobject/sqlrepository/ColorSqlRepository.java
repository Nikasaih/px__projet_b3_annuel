package com.backend.store.dataobject.sqlrepository;

import com.backend.store.dataobject.sqlentity.ColorSqlEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorSqlRepository extends CrudRepository<ColorSqlEntity, Long> {
}
