package com.backend.storerate.dataobject.protobilling.repository;

import com.backend.storerate.dataobject.protobilling.repository.abs.BoxSqlRepositoryAbs;
import com.backend.storerate.dataobject.protobilling.sqlentity.FavoriteSqlEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritesSqlRepository extends BoxSqlRepositoryAbs<FavoriteSqlEntity> {
}
