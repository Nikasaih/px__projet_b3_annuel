package com.backend.storerate.dataobject.sqlrepository.el;

import com.backend.storerate.dataobject.sqlrepository.abs.BoxElementSqlRepositoryAbs;
import com.backend.storerate.dataobject.sqlentity.BasketElementSqlEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketElementRepository extends BoxElementSqlRepositoryAbs<BasketElementSqlEntity> {
}
