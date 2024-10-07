package com.fintech.pob.domain.userToken.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table
@Data
@NoArgsConstructor
public class UserToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID userKey; // userKey로 푸시 토큰을 관리

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime created;

    // 생성자, getter, setter 추가
    public UserToken(UUID userKey, String token) {
        this.userKey = userKey;
        this.token = token;
        this.created = LocalDateTime.now();
    }
}


