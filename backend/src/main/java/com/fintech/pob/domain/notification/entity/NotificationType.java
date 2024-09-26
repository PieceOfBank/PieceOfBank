package com.fintech.pob.domain.notification.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
public class NotificationType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationTypeId;
    private String typeName;
}
