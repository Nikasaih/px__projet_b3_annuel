package btree.projetpro.backend.material;

import btree.projetpro.backend.util.dto.Entities;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MaterialEntity extends Entities {
    private String name;
    private String type;
}
