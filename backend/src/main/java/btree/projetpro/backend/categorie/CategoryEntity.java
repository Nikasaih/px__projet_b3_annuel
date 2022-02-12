package btree.projetpro.backend.categorie;

import btree.projetpro.backend.util.dto.Entities;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CategoryEntity extends Entities {
    private String room;
}
