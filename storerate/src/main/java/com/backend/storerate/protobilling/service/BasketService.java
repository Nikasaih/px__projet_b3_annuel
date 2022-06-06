package com.backend.storerate.protobilling.service;

import com.backend.storerate.protobilling.repository.el.BasketElementRepository;
import com.backend.storerate.protobilling.repository.grp.BasketGrpSqlRepository;
import com.backend.storerate.protobilling.request.AddBasketElementRequest;
import com.backend.storerate.protobilling.sqlentity.BasketGrpSqlEntity;
import com.backend.storerate.protobilling.sqlentity.Box;
import com.backend.storerate.protobilling.sqlentity.nosqlentity.BoxGrpAbs;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BasketService extends BoxServiceAbs<AddBasketElementRequest, BasketGrpSqlEntity, Box> {
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
    public BoxGrpAbs<Box> addUpdateElement(AddBasketElementRequest newElReq) {
        ModelMapper mapper = new ModelMapper();
        Box newElement = mapper.map(newElReq, Box.class);
        BasketGrpSqlEntity allElementOfCustomer = getGrpByCustomerId(newElReq.getCustomerId());
        newElement.setGrpEntity(allElementOfCustomer);
        elementRepository.save(newElement);

        return allElementOfCustomer.addElement(newElement);
    }
}
