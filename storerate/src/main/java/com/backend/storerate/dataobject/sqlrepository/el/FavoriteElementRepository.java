package com.backend.storerate.dataobject.sqlrepository.el;

import com.backend.storerate.dataobject.sqlrepository.abs.BoxElementSqlRepositoryAbs;
import com.backend.storerate.dataobject.sqlentity.FavoriteElementSqlEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteElementRepository extends BoxElementSqlRepositoryAbs<FavoriteElementSqlEntity> {
}
