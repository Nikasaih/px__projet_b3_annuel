package com.backend.storerate.service;

import com.backend.storerate.dataobject.sqlrepository.el.FavoriteElementRepository;
import com.backend.storerate.dataobject.sqlrepository.grp.FavoriteGrpSqlRepository;
import com.backend.storerate.dataobject.request.AddFavoriteElementRequest;
import com.backend.storerate.dataobject.request.RemoveFavoriteElementRequest;
import com.backend.storerate.dataobject.sqlentity.FavoriteElementSqlEntity;
import com.backend.storerate.dataobject.sqlentity.FavoriteGrpSqlEntity;
import com.backend.storerate.dataobject.nosqlentity.BoxGrpAbs;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FavoriteService extends BoxServiceAbs<AddFavoriteElementRequest, FavoriteGrpSqlEntity, FavoriteElementSqlEntity, RemoveFavoriteElementRequest> {
    @Autowired
    FavoriteGrpSqlRepository grpRepository;
    @Autowired
    FavoriteElementRepository elementRepository;

    private FavoriteGrpSqlEntity generateNewBoxSqlEntity(Long customerId) {
        FavoriteGrpSqlEntity newBoxSqlEntity = new FavoriteGrpSqlEntity();
        newBoxSqlEntity.setCustomerId(customerId);
        return newBoxSqlEntity;
    }

    @Override
    public FavoriteGrpSqlEntity getGrpByCustomerId(Long customerId) {
        Optional<FavoriteGrpSqlEntity> boxSqlEntity = grpRepository.findByCustomerId(customerId);
        if (boxSqlEntity.isEmpty()) {
            return grpRepository.save(generateNewBoxSqlEntity(customerId));
        }
        return boxSqlEntity.get();
    }

    @Override
    public FavoriteGrpSqlEntity removeElement(RemoveFavoriteElementRequest request) {
        FavoriteGrpSqlEntity grpByCustomerId = getGrpByCustomerId(request.getCustomerId());
        grpByCustomerId.getBoxElements().stream()
                .forEach(e -> {
                    if (e.getBoxEmb().getArticleId() != request.getArticleId()) {
                        return;
                    }
                    grpByCustomerId.removeElement(e);
                });

        return grpRepository.save(grpByCustomerId);
    }


    @Override
    public BoxGrpAbs<FavoriteElementSqlEntity> addUpdateElement(AddFavoriteElementRequest newElReq) {
        ModelMapper mapper = new ModelMapper();
        FavoriteElementSqlEntity newElement = mapper.map(newElReq, FavoriteElementSqlEntity.class);
        FavoriteGrpSqlEntity allElementOfCustomer = getGrpByCustomerId(newElReq.getCustomerId());
        newElement.setGrpEntity(allElementOfCustomer);
        elementRepository.save(newElement);


        return allElementOfCustomer.addElement(newElement);
    }
}
