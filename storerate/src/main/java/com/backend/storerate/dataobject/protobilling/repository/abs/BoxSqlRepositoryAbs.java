package com.backend.storerate.dataobject.protobilling.repository.abs;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface BoxSqlRepositoryAbs<GrpElementEntity> extends CrudRepository<GrpElementEntity, Long> {
    Optional<GrpElementEntity> findByCustomerId(Long customerId);
}
