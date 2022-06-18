package com.backend.storerate.service.redirection;

import com.backend.storerate.dataobject.request.ChangeArticleGradeRequest;
import com.backend.storerate.dataobject.sqlentity.CommentSqlEntity;
import com.backend.storerate.dataobject.sqlrepository.CommentSqlRepository;
import com.backend.storerate.service.RedirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreArticleService {
    @Autowired
    CommentSqlRepository commentSqlRepository;
    @Value("${microservices.store}")
    String storeRootUrl;
    @Autowired
    RedirectionService redirectionService;
    String redirectionControllerUrl = "/articles";

    public void updateArticleGradeAndCustomerNumberByArticleId(Long articlesId) {
        List<CommentSqlEntity> commentSqlEntity = commentSqlRepository.findByArticleId(articlesId);
        Float grade = 0f;

        for (int i = 0; i < commentSqlEntity.size(); i++) {
            grade += commentSqlEntity.get(i).getGrade();
        }
        ChangeArticleGradeRequest changeArticleGradeRequest = new ChangeArticleGradeRequest();
        changeArticleGradeRequest.setArticleId(articlesId);
        changeArticleGradeRequest.setNewGrade(grade);
        changeArticleGradeRequest.setCustomerNumber(commentSqlEntity.size());

        redirectionService.redirect(changeArticleGradeRequest, HttpMethod.POST, storeRootUrl + redirectionControllerUrl + "/update-grade");
    }
}
