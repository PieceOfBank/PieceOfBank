package com.fintech.pob.domain.notification.service.notification;

import com.fintech.pob.domain.notification.dto.NotificationResponseDto;
import com.fintech.pob.domain.subscriptionApproval.dto.SubscriptionApprovalKeyDto;
import com.fintech.pob.domain.subscriptionApproval.dto.SubscriptionApprovalRequestDto;
import com.fintech.pob.domain.subscriptionApproval.dto.SubscriptionApprovalResponseDto;
import com.fintech.pob.domain.transactionApproval.dto.TransactionApprovalRequestDto;
import com.fintech.pob.domain.transactionApproval.dto.TransactionApprovalResponseDto;
import com.fintech.pob.domain.notification.entity.Notification;
import com.fintech.pob.domain.notification.entity.NotificationStatus;
import com.fintech.pob.domain.notification.entity.NotificationType;
import com.fintech.pob.domain.subscriptionApproval.entity.SubscriptionApproval;
import com.fintech.pob.domain.subscriptionApproval.entity.SubscriptionApprovalStatus;
import com.fintech.pob.domain.transactionApproval.entity.TransactionApproval;
import com.fintech.pob.domain.transactionApproval.entity.TransactionApprovalStatus;
import com.fintech.pob.domain.notification.repository.notification.NotificationRepository;
import com.fintech.pob.domain.notification.repository.notification.NotificationTypeRepository;
import com.fintech.pob.domain.notification.repository.subscription.SubscriptionApprovalRepository;
import com.fintech.pob.domain.notification.repository.transaction.TransactionApprovalRepository;
import com.fintech.pob.domain.subscription.dto.SubscriptionRequestDto;
import com.fintech.pob.domain.subscription.service.SubscriptionService;
import com.fintech.pob.domain.user.entity.User;
import com.fintech.pob.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final SubscriptionApprovalRepository subscriptionApprovalRepository;
    private final SubscriptionService subscriptionService;

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
    @Transactional
    public Long requestSubscription(SubscriptionApprovalRequestDto subscriptionApprovalRequestDto) {

        NotificationType notificationType = notificationTypeRepository.findByTypeName("구독 신청 알림")
                .orElseThrow(() -> new IllegalArgumentException("Notification Type not found"));
        User receiver = userRepository.findByUserId(subscriptionApprovalRequestDto.getReceiverId())
                .orElseThrow(() -> new IllegalArgumentException("Receiver user not found"));
        User sender = userRepository.findByUserKey(subscriptionApprovalRequestDto.getSenderKey())
                .orElseThrow(() -> new IllegalArgumentException("Sender user not found"));

        // Notification 생성
        Notification notification = Notification.builder()
                .senderUser(sender)
                .receiverUser(receiver)
                .type(notificationType)
                .created(LocalDateTime.now())
                .notificationStatus(NotificationStatus.UNREAD)
                .build();
        notificationRepository.save(notification);

        // SubscriptionApproval 생성
        SubscriptionApproval subscriptionApproval = SubscriptionApproval.builder()
                .notification(notification)
                .requesterName(sender.getUserName())
                .status(SubscriptionApprovalStatus.PENDING).build();
        SubscriptionApproval savedApproval = subscriptionApprovalRepository.save(subscriptionApproval);

        return savedApproval.getSubscriptionApprovalId();
    }

    @Override
    public SubscriptionApprovalResponseDto getSubscriptionApprovalBySubscriptionId(Long subscriptionId) {
        SubscriptionApproval subscriptionApproval = subscriptionApprovalRepository.findById(subscriptionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 알림 내역이 없습니다."));

        return SubscriptionApprovalResponseDto.builder()
                .requesterName(subscriptionApproval.getRequesterName())
                .build();
    }

    @Override
    @Transactional
    public SubscriptionApprovalKeyDto approveSubscriptionRequest(Long subscriptionApprovalId) {
        SubscriptionApproval subscriptionApproval = subscriptionApprovalRepository.findById(subscriptionApprovalId)
                .orElseThrow(() -> new IllegalArgumentException("Subscription Approval not found"));
        subscriptionApproval.setStatus(SubscriptionApprovalStatus.APPROVED);
        subscriptionApprovalRepository.save(subscriptionApproval);





        Notification notification = subscriptionApproval.getNotification();

        // 수락시 저장하는 로직 - 정민
        SubscriptionRequestDto subscriptionRequestDto = new SubscriptionRequestDto();
        subscriptionRequestDto.setTargetKey(notification.getSenderUser().getUserKey());
        subscriptionRequestDto.setUserKey(notification.getReceiverUser().getUserKey());
        subscriptionService.create(subscriptionRequestDto);
        
        notification.setNotificationStatus(NotificationStatus.READ); // 읽음 처리
        notificationRepository.save(notification);

        return SubscriptionApprovalKeyDto.builder()
                .targetKey(notification.getSenderUser().getUserKey())
                .protectKey(notification.getReceiverUser().getUserKey())
                .build();
    }

    @Override
    public SubscriptionApprovalKeyDto refuseSubscriptionRequest(Long subscriptionApprovalId) {
        SubscriptionApproval subscriptionApproval = subscriptionApprovalRepository.findById(subscriptionApprovalId)
                .orElseThrow(() -> new IllegalArgumentException("Subscription Approval not found"));
        subscriptionApproval.setStatus(SubscriptionApprovalStatus.REFUSED);
        subscriptionApprovalRepository.save(subscriptionApproval);

        Notification notification = subscriptionApproval.getNotification();

        return SubscriptionApprovalKeyDto.builder()
                .targetKey(notification.getSenderUser().getUserKey())
                .protectKey(notification.getReceiverUser().getUserKey())
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

