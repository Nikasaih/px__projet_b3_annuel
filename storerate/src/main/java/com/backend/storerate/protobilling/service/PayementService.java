package com.backend.storerate.protobilling.service;

import com.backend.storerate.protobilling.repository.grp.BillGrpSqlRepository;
import com.backend.storerate.protobilling.request.PaymentRequest;
import com.backend.storerate.protobilling.sqlentity.BasketGrpSqlEntity;
import com.backend.storerate.protobilling.sqlentity.BillElementSqlEntity;
import com.backend.storerate.protobilling.sqlentity.BillGrpSqlEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PayementService {
    @Autowired
    BasketService basketService;
    ModelMapper mapper = new ModelMapper();
    @Autowired
    BillGrpSqlRepository billGrpSqlRepository;

    public void pay(PaymentRequest request) {
        BasketGrpSqlEntity basketGrp = basketService.getGrpByCustomerId(request.getCustomerId());

        BillGrpSqlEntity newBillGrp = mapper.map(basketGrp, BillGrpSqlEntity.class);

        Set<BillElementSqlEntity> bills = basketGrp.getBoxElements().stream()
                .map(e -> mapper.map(e, BillElementSqlEntity.class)).collect(Collectors.toSet());

        newBillGrp.setBoxElements(bills);
        if (bills.size() <= 0) {
            return;
        }
        //Todo Stripe Check

        billGrpSqlRepository.save(newBillGrp);
        basketService.clearBasket(request.getCustomerId());

    }
}
