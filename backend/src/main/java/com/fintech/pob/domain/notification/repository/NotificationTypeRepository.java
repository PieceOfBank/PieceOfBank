package com.fintech.pob.domain.notification.repository;

import com.fintech.pob.domain.notification.entity.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationTypeRepository extends JpaRepository<NotificationType, Long> {
}