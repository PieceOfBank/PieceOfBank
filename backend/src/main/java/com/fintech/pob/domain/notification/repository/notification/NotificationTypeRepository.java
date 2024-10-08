package com.fintech.pob.domain.notification.repository.notification;

import com.fintech.pob.domain.notification.entity.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationTypeRepository extends JpaRepository<NotificationType, Long> {
    Optional<NotificationType> findByTypeName(String typeName);
}