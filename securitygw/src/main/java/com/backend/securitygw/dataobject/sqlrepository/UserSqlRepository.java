package com.backend.securitygw.dataobject.sqlrepository;

import com.backend.securitygw.dataobject.sqlentity.UserSqlEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface UserSqlRepository extends CrudRepository<UserSqlEntity, Long> {
    Optional<UserSqlEntity> findByEmail(String email);
}
