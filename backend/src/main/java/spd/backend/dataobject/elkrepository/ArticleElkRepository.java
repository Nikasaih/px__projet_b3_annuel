package spd.backend.dataobject.elkrepository;

import spd.backend.dataobject.elkentity.ArticleElkEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleElkRepository extends ElasticsearchRepository<ArticleElkEntity, Long> {
}
