package com.backend.store.dataobject.elkrepository;

import com.backend.store.dataobject.elkentity.ArticleElkEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleElkRepository extends ElasticsearchRepository<ArticleElkEntity, Long> {
}
