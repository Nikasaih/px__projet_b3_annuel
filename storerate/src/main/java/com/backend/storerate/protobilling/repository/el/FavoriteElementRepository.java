package com.backend.storerate.protobilling.repository.el;

import com.backend.storerate.protobilling.repository.abs.BoxElementSqlRepositoryAbs;
import com.backend.storerate.protobilling.sqlentity.FavoriteElementSqlEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteElementRepository extends BoxElementSqlRepositoryAbs<FavoriteElementSqlEntity> {
}
