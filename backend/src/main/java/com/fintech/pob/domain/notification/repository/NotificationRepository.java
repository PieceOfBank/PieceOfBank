package com.fintech.pob.domain.notification.repository;

import com.fintech.pob.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}