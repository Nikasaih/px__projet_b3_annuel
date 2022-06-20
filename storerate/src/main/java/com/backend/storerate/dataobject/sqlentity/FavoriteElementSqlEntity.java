package com.backend.storerate.dataobject.sqlentity;

import com.backend.storerate.dataobject.nosqlentity.BoxElementAbs;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class FavoriteElementSqlEntity extends BoxElementAbs<FavoriteGrpSqlEntity> {
}
