package com.fintech.pob.domain.notification.repository.notification;

import com.fintech.pob.domain.notification.entity.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByReceiverUser_UserKey(UUID receiverKey);
}