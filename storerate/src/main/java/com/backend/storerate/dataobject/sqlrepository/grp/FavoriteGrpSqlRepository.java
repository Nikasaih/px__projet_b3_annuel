package com.backend.storerate.dataobject.sqlrepository.grp;

import com.backend.storerate.dataobject.sqlrepository.abs.BoxGrpSqlRepositoryAbs;
import com.backend.storerate.dataobject.sqlentity.FavoriteGrpSqlEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteGrpSqlRepository extends BoxGrpSqlRepositoryAbs<FavoriteGrpSqlEntity> {
}
