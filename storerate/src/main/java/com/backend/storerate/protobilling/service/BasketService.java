package com.backend.storerate.protobilling.service;

import com.backend.storerate.protobilling.repository.el.BasketElementRepository;
import com.backend.storerate.protobilling.repository.grp.BasketGrpSqlRepository;
import com.backend.storerate.protobilling.request.AddBasketElementRequest;
import com.backend.storerate.protobilling.request.RemoveBasketElementRequest;
import com.backend.storerate.protobilling.sqlentity.BasketElementSqlEntity;
import com.backend.storerate.protobilling.sqlentity.BasketGrpSqlEntity;
import com.backend.storerate.protobilling.sqlentity.embedable.BasketElEmb;
import com.backend.storerate.protobilling.sqlentity.nosqlentity.BoxGrpAbs;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BasketService extends BoxServiceAbs<AddBasketElementRequest, BasketGrpSqlEntity, BasketElementSqlEntity, RemoveBasketElementRequest> {
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
    public BasketGrpSqlEntity removeElement(RemoveBasketElementRequest request) {
        BasketGrpSqlEntity grpByCustomerId = getGrpByCustomerId(request.getCustomerId());
        grpByCustomerId.getBoxElements().stream()
                .forEach(e -> {
                    if (e.getBoxEmb().getArticleId() != request.getArticleId()) {
                        return;
                    }
                    BasketElEmb boxElEmb = e.getBasketEmb();
                    Long count = boxElEmb.getAmount() - request.getAmount();
                    grpByCustomerId.removeElement(e);
                    if (count < 1) {
                        return;
                    }
                    boxElEmb.setAmount(count);
                    e.setBasketEmb(boxElEmb);
                    grpByCustomerId.addElement(e);
                });

        return grpRepository.save(grpByCustomerId);
    }

    @Override
    public BoxGrpAbs<BasketElementSqlEntity> addUpdateElement(AddBasketElementRequest newElReq) {
        ModelMapper mapper = new ModelMapper();
        BasketElementSqlEntity newElement = mapper.map(newElReq, BasketElementSqlEntity.class);
        BasketGrpSqlEntity allElementOfCustomer = getGrpByCustomerId(newElReq.getCustomerId());
        newElement.setGrpEntity(allElementOfCustomer);
        elementRepository.save(newElement);

        return allElementOfCustomer.addElement(newElement);
    }

    public void clearBasket(Long customerId) {
        grpRepository.delete(getGrpByCustomerId(customerId));
    }
}
