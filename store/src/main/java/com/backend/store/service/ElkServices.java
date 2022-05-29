package com.backend.store.service;

import com.backend.store.dataobject.request.elk.ElkTraversalRequest;
import com.backend.store.dataobject.request.elk.FuzzyRequest;
import com.backend.store.dataobject.request.elk.search.*;
import com.backend.store.dataobject.sqlentity.ArticleSqlEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;

@Service
public class ElkServices {
    RestTemplate restTemplate = new RestTemplate();
    @Value("${elasticsearch.url}")
    String elkBaseUrl;
    @Autowired
    RedirectionService redirectionService;

    public ResponseEntity<String> fuzzySearch(ElkTraversalRequest request) {
        Bool shouldHandler = new Bool(new HashSet<>());
        request.getCategory().stream().forEach(category -> {
            MatchCategory matchCategory = new MatchCategory(category);
            MatchHolder matchHolder = new MatchHolder(matchCategory);
            shouldHandler.getShould().add(matchHolder);
        });
        Name nameQuery = new Name();
        nameQuery.setQuery(request.getName());
        MatchName matchName = new MatchName(nameQuery);
        MatchHolder matchHolder = new MatchHolder(matchName);

        shouldHandler.getShould().add(matchHolder);
        FuzzyRequest fuzzyRequest = new FuzzyRequest(new Query(shouldHandler));

        return restTemplate.postForEntity(elkBaseUrl + "/articles/_search", fuzzyRequest, String.class);
    }

    public void createFromSql(ArticleSqlEntity articleSql) {
        ElkTraversalRequest elkTraversalRequest = new ElkTraversalRequest();
        elkTraversalRequest.setName(articleSql.getName());
        articleSql.getCategories().stream().forEach(e -> elkTraversalRequest.getCategory().add(e.getRoom()));
        articleSql.getMaterials().stream().forEach(e -> elkTraversalRequest.getCategory().add(e.getName()));
        articleSql.getColors().stream().forEach(e -> elkTraversalRequest.getCategory().add(e.getName()));
        elkTraversalRequest.setArticleId(articleSql.getId());

        createDocument(elkTraversalRequest);
        return;
    }

    public ResponseEntity<Object> createDocument(ElkTraversalRequest request) {
        return redirectionService.redirect(request, HttpMethod.POST, elkBaseUrl + "/articles/_doc/");
    }
}
