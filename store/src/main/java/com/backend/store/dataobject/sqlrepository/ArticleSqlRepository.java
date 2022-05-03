package com.backend.store.dataobject.sqlrepository;

import com.backend.store.dataobject.sqlentity.ArticleSqlEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleSqlRepository extends CrudRepository<ArticleSqlEntity, Long> {
}
