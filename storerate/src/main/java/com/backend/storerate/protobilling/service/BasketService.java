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
public class BasketService extends BoxServiceAbs<AddBasketElementRequest> {
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
    public Object getAllElement(Long customerId) {
        Optional<BasketGrpSqlEntity> boxSqlEntity = grpRepository.findByCustomerId(customerId);
        if (boxSqlEntity.isEmpty()) {
            return grpRepository.save(generateNewBoxSqlEntity(customerId));
        }
        return boxSqlEntity.get();
    }

    @Override
    public Object removeElement(Long customerId, Long articleId) {
        BasketGrpSqlEntity allElementOfCustomer = (BasketGrpSqlEntity) getAllElement(customerId);
        //     allElementOfCustomer.removeContentByArticleId(articleId);
        grpRepository.save(allElementOfCustomer);

        return allElementOfCustomer;
    }

    @Override
    public Object addUpdateElement(AddBasketElementRequest newElReq) {
        ModelMapper mapper = new ModelMapper();
        BasketElementSqlEntity newBasketElement = mapper.map(newElReq, BasketElementSqlEntity.class);
        BasketGrpSqlEntity allElementOfCustomer = (BasketGrpSqlEntity) getAllElement(newElReq.getCustomerId());
        newBasketElement.setGrpEntity(allElementOfCustomer);
        elementRepository.save(newBasketElement);
        // allElementOfCustomer.getBoxElements().add(newBasketEl);
        grpRepository.save(allElementOfCustomer);
        return grpRepository.findAll();
    }
}
