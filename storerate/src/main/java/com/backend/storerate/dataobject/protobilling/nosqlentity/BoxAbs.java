package com.backend.storerate.dataobject.protobilling.nosqlentity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@MappedSuperclass
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BoxAbs<ElementEntity extends BoxElementAbs> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long customerId;
    @JsonIdentityReference(alwaysAsId = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToMany(mappedBy = "id", cascade = CascadeType.PERSIST)
    private Set<ElementEntity> boxElements = new HashSet<>();

    public BoxAbs addUpdateContent(ElementEntity newBoxElement) {
        Optional<ElementEntity> boxElementSqlOpt = boxElements.stream().filter(e -> e.getArticleId().equals(newBoxElement.getArticleId())).findFirst();
        if (boxElementSqlOpt.isPresent()) {
            boxElements.remove(boxElementSqlOpt.get());
        }
        boxElements.add(newBoxElement);
        return this;
    }

    public BoxAbs removeContentByArticleId(Long articleId) {
        Optional<ElementEntity> boxElementSqlOpt = boxElements.stream().filter(e -> e.getArticleId().equals(articleId)).findFirst();
        if (boxElementSqlOpt.isPresent()) {
            boxElements.remove(boxElementSqlOpt.get());
        }

        return this;
    }
}
