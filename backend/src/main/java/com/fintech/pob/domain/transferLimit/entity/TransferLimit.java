package com.fintech.pob.domain.transferLimit.entity;

import com.fintech.pob.domain.notification.entity.Notification;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
public class TransferLimit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionApprovalId;

    @OneToOne
    @JoinColumn(nullable = false)
    private Notification notification;

    @Column(nullable = false)
    private Long newLimit;

    @Column(nullable = false)
    private Long oldLimit;

    @Builder
    public TransferLimit(Notification notification, Long newLimit, Long oldLimit) {
        this.notification = notification;
        this.newLimit = newLimit;
        this.oldLimit = oldLimit;
    }
}
