package btree.projetpro.backend.security.dao;

import btree.projetpro.backend.dao.util.persistenceservice.Entities;
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
