package btree.projetpro.backend.color;

import btree.projetpro.backend.util.persistenceservice.Entities;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ColorEntity extends Entities {
    private String name;
    private String hexacode;
}
