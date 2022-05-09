package com.backend.securitygw.dataobject.sqlentity;

import com.backend.securitygw.common.enumerator.ConfirmationTokenType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime expiredAt;
    private LocalDateTime confirmedAt;
    @Enumerated(EnumType.STRING)
    private ConfirmationTokenType confirmationTokenType;

    @ManyToOne
    @JoinColumn(nullable = false, name = "app_user_id")
    private UserSqlEntity appUser;

    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiredAt, UserSqlEntity appUser, ConfirmationTokenType confirmationTokenType) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.appUser = appUser;
        this.confirmationTokenType = confirmationTokenType;
    }
}
