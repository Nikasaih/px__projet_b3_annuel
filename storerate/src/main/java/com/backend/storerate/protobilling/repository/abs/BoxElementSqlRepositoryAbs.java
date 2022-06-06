package com.backend.storerate.protobilling.repository.abs;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BoxElementSqlRepositoryAbs<ElementEntity> extends CrudRepository<ElementEntity, Long> {
}
