package com.backend.storerate.dataobject.protobilling.service;

import com.backend.storerate.dataobject.protobilling.repository.BasketSqlRepository;
import com.backend.storerate.dataobject.protobilling.sqlentity.FavoriteElementSqlEntity;
import com.backend.storerate.dataobject.protobilling.sqlentity.FavoriteSqlEntity;
import org.springframework.stereotype.Service;

@Service
public class FavoritesService extends BoxServiceAbs<BasketSqlRepository, FavoriteSqlEntity, FavoriteElementSqlEntity> {
}
