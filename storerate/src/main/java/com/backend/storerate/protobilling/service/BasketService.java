package com.backend.storerate.protobilling.service;

import com.backend.storerate.protobilling.repository.el.BasketElementRepository;
import com.backend.storerate.protobilling.repository.grp.BasketGrpSqlRepository;
import com.backend.storerate.protobilling.request.AddBasketElementRequest;
import com.backend.storerate.protobilling.sqlentity.BasketElementSqlEntity;
import com.backend.storerate.protobilling.sqlentity.BasketGrpSqlEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BasketService extends BoxServiceAbs<AddBasketElementRequest, BasketGrpSqlEntity> {
    @Autowired
    BasketGrpSqlRepository grpRepository;
    @Autowired
    BasketElementRepository elementRepository;

    private BasketGrpSqlEntity generateNewBoxSqlEntity(Long customerId) {
        BasketGrpSqlEntity newBoxSqlEntity = new BasketGrpSqlEntity();
        newBoxSqlEntity.setCustomerId(customerId);
        return newBoxSqlEntity;
    }

    @Override
    public BasketGrpSqlEntity getGrpByCustomerId(Long customerId) {
        Optional<BasketGrpSqlEntity> boxSqlEntity = grpRepository.findByCustomerId(customerId);
        if (boxSqlEntity.isEmpty()) {
            return grpRepository.save(generateNewBoxSqlEntity(customerId));
        }
        return boxSqlEntity.get();
    }

    @Override
    public BasketGrpSqlEntity removeElement(Long customerId, Long articleId) {
        BasketGrpSqlEntity allElementOfCustomer = getGrpByCustomerId(customerId);
        grpRepository.save(allElementOfCustomer);

        return allElementOfCustomer;
    }

    @Override
    public BasketGrpSqlEntity addUpdateElement(AddBasketElementRequest newElReq) {
        ModelMapper mapper = new ModelMapper();
        BasketElementSqlEntity newBasketElement = mapper.map(newElReq, BasketElementSqlEntity.class);
        BasketGrpSqlEntity allElementOfCustomer = getGrpByCustomerId(newElReq.getCustomerId());
        newBasketElement.setGrpEntity(allElementOfCustomer);
        elementRepository.save(newBasketElement);

        return getGrpByCustomerId(newElReq.getCustomerId());
    }
}
