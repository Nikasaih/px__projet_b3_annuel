package btree.projetpro.backend.dao.util.persistenceservice;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class Entities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
