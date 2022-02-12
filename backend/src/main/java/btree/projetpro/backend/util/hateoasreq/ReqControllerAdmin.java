package btree.projetpro.backend.util.hateoasreq;

import btree.projetpro.backend.article.ArticleEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface ReqControllerAdmin {
    public ResponseEntity<ArticleEntity> deleteOne(@PathVariable("id") Long id);

}
