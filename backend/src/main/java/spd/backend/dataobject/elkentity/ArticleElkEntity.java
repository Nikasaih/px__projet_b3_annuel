package spd.backend.dataobject.elkentity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

@Setter
@Getter
@Document(indexName = "article")
public class ArticleElkEntity {
    private Long id;

}
