package com.fintech.pob.domain.user.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "user")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_key", unique = true, nullable = false, updatable = false)
    private String userKey;

    @Column(name = "user_name", nullable = false, length = 50)
    private String userName;

    @Column(name = "user_password", nullable = false)
    private String userPassword;  // 6자리 PIN 암호화하여 저장

    @Column(name = "created", nullable = false, updatable = false)
    private LocalDateTime created;

    @Column(name = "updated", nullable = false)
    private LocalDateTime updated;

    @Column(name = "subscription_type", nullable = false)
    private int subscriptionType; // 0: Default, 1: 보호자, 2: 피보호자

    @PrePersist
    protected void onCreate() {
        this.created = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated = LocalDateTime.now();
    }
}
