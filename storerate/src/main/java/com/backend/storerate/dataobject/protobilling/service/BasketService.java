package com.backend.storerate.dataobject.protobilling.service;

import com.backend.storerate.dataobject.protobilling.repository.BasketSqlRepository;
import com.backend.storerate.dataobject.protobilling.sqlentity.BasketElementSqlEntity;
import com.backend.storerate.dataobject.protobilling.sqlentity.BasketSqlEntity;
import org.springframework.stereotype.Service;

@Service
public class BasketService extends BoxServiceAbs<BasketSqlRepository, BasketSqlEntity, BasketElementSqlEntity> {
}
