package btree.projetpro.backend.categorie;

import btree.projetpro.backend.util.persistenceservice.Entities;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CategoryEntity extends Entities {
    private String room;
}
