package com.backend.storerate.service.redirection;

import com.backend.storerate.dataobject.sqlentity.CommentSqlEntity;
import com.backend.storerate.dataobject.sqlrepository.CommentSqlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class StoreArticleService {
    @Autowired
    CommentSqlRepository commentSqlRepository;
    @Value("${microservices.store}")
    String storeRootUrl;
    @Autowired
    RestTemplate restTemplate;
    String redirectionControllerUrl = "/api/articles";

    public void UpdateArticleGradeAndCustomerNumberByArticleId(Long articlesId) {
        List<CommentSqlEntity> commentSqlEntity = commentSqlRepository.findByArticleId(articlesId);
        Float grade = 0f;

        for (int i = 0; i < commentSqlEntity.size(); i++) {
            grade += commentSqlEntity.get(i).getGrade();
        }

        restTemplate.getForEntity(storeRootUrl + redirectionControllerUrl + "/update-grade", Object.class);
    }
}
