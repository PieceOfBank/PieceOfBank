package com.fintech.pob.domain.notification.entity;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "subscription_approval")
@Data
@NoArgsConstructor
public class SubscriptionApproval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subscriptionApprovalId;

    @OneToOne
    @JoinColumn(name = "notification_id", nullable = false)
    private Notification notification;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionApprovalStatus status; // 승인 상태

    @Builder
    public SubscriptionApproval(Notification notification, SubscriptionApprovalStatus status) {
        this.notification = notification;
        this.status = status;
    }
}
