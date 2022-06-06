package com.backend.storerate.protobilling.repository.el;

import com.backend.storerate.protobilling.repository.abs.BoxElementSqlRepositoryAbs;
import com.backend.storerate.protobilling.sqlentity.BasketElementSqlEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketElementRepository extends BoxElementSqlRepositoryAbs<BasketElementSqlEntity> {
}
