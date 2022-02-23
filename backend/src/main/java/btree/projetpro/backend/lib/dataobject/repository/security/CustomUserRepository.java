package btree.projetpro.backend.lib.dataobject.repository.security;

import btree.projetpro.backend.lib.dataobject.entity.security.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface CustomUserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);
}
