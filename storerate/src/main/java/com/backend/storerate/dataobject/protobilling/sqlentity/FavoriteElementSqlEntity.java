package com.backend.storerate.dataobject.protobilling.sqlentity;

import com.backend.storerate.dataobject.protobilling.nosqlentity.BoxElementAbs;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class FavoriteElementSqlEntity extends BoxElementAbs<FavoriteSqlEntity> {
}
