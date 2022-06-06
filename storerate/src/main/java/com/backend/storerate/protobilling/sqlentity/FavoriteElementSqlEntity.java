package com.backend.storerate.protobilling.sqlentity;

import com.backend.storerate.protobilling.sqlentity.nosqlentity.BoxElementAbs;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class FavoriteElementSqlEntity extends BoxElementAbs<FavoriteGrpSqlEntity> {
}
