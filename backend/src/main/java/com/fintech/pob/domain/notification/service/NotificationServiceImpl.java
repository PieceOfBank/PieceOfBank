package com.fintech.pob.domain.notification.service;

import com.fintech.pob.domain.notification.dto.NotificationResponseDto;
import com.fintech.pob.domain.notification.entity.Notification;
import com.fintech.pob.domain.notification.entity.NotificationStatus;
import com.fintech.pob.domain.notification.entity.NotificationType;
import com.fintech.pob.domain.notification.repository.NotificationRepository;
import com.fintech.pob.domain.notification.repository.NotificationTypeRepository;
import com.fintech.pob.domain.user.entity.User;
import com.fintech.pob.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationTypeRepository notificationTypeRepository;
    private final UserRepository userRepository;

    @Override
    public List<NotificationResponseDto> getAllNotificationsByReceiverKey(UUID receiverKey) {
        List<Notification> notifications = notificationRepository.findByReceiverUser_UserKey(receiverKey);
        return notifications.stream().map(this::convertToNotificationDto).collect(Collectors.toList());
    }

    @Override
    public NotificationResponseDto getNotificationByNotificationId(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 알림 내역이 없습니다."));
        return convertToNotificationDto(notification);
    }

    @Override
    public NotificationResponseDto updateNotificationStatusToRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 알림 내역이 없습니다."));
        notification.setNotificationStatus(NotificationStatus.READ);
        notification.setRead_at(LocalDateTime.now());
        notificationRepository.save(notification);
        return convertToNotificationDto(notification);
    }

    @Override
    public NotificationResponseDto updateNotificationStatusToDelete(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 알림 내역이 없습니다."));
        notification.setNotificationStatus(NotificationStatus.DELETED);
        notification.setRead_at(LocalDateTime.now());
        notificationRepository.save(notification);
        return convertToNotificationDto(notification);
    }

    private NotificationResponseDto convertToNotificationDto(Notification notification) {
        return NotificationResponseDto.builder()
                .notificationId(notification.getNotificationId())
                .senderKey(notification.getSenderUser().getUserKey())
                .receiverKey(notification.getReceiverUser().getUserKey())
                .notificationType(notification.getType().getTypeName())
                .created(notification.getCreated())
                .readAt(notification.getRead_at())
                .notificationStatus(notification.getNotificationStatus())
                .build();
    }

    @Override
    public Long sendNotification(UUID senderKey, UUID receiverKey, String typeName) {
        User senderUser = userRepository.findByUserKey(senderKey)
                .orElseThrow(() -> new IllegalArgumentException("보내는 유저를 찾을 수 없습니다: " + senderKey));
        User receiverUser = userRepository.findByUserKey(receiverKey)
                .orElseThrow(() -> new IllegalArgumentException("받는 유저를 찾을 수 없습니다: " + receiverKey));
        NotificationType type = notificationTypeRepository.findByTypeName(typeName)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 알림 유형입니다: " + typeName));

        Notification notification = Notification.builder()
                .senderUser(senderUser)
                .receiverUser(receiverUser)
                .type(type)
                .notificationStatus(NotificationStatus.UNREAD)
                .build();

        notificationRepository.save(notification);
        return notification.getNotificationId();
    }
}

