package btree.projetpro.backend.comment;

import btree.projetpro.backend.util.persistenceservice.Entities;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CommentEntity extends Entities {
    private Float grade;
    private String text;

}
