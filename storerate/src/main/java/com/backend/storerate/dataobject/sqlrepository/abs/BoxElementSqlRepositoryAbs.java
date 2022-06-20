package com.backend.storerate.dataobject.sqlrepository.abs;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BoxElementSqlRepositoryAbs<ElementEntity> extends CrudRepository<ElementEntity, Long> {
}
