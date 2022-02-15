package btree.projetpro.backend.lib.dataobject.entity;

import btree.projetpro.backend.lib.common.enumerator.UserRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@ToString
@Getter
@Setter
public class User extends Entities {
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER_ROLE;
}
