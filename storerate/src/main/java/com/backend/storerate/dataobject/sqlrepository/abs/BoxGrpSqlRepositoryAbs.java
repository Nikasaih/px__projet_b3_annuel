package com.backend.storerate.dataobject.sqlrepository.abs;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface BoxGrpSqlRepositoryAbs<GrpElementEntity> extends CrudRepository<GrpElementEntity, Long> {
    Optional<GrpElementEntity> findByCustomerId(Long customerId);
}
