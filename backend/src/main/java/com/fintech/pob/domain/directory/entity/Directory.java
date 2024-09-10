package com.fintech.pob.domain.directory.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "directory")
@Data
@NoArgsConstructor
public class Directory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "directoryId")
    private Integer directoryId;

    @ManyToOne
    @JoinColumn(name = "userKey", nullable = false)
    private User user; // User Entity 연결 필요

    @Column(name = "accountNo", nullable = false)
    private String accountNo;

    @Column(name = "institutionCode", nullable = false)
    private Integer institutionCode;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime created;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updated;

    @PrePersist
    protected void onCreate() {
        this.created = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated = LocalDateTime.now();
    }
}