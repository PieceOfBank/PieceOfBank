package com.fintech.pob.domain.notification.service.notification;

import com.fintech.pob.domain.notification.dto.NotificationResponseDto;
import com.fintech.pob.domain.subscriptionApproval.dto.SubscriptionApprovalKeyDto;
import com.fintech.pob.domain.subscriptionApproval.dto.SubscriptionApprovalRequestDto;
import com.fintech.pob.domain.subscriptionApproval.dto.SubscriptionApprovalResponseDto;
import com.fintech.pob.domain.transactionApproval.dto.TransactionApprovalRequestDto;
import com.fintech.pob.domain.transactionApproval.dto.TransactionApprovalResponseDto;

import java.util.List;
import java.util.UUID;

public interface NotificationService {
    List<NotificationResponseDto> getAllNotificationsByReceiverKey(UUID receiverKey);
    NotificationResponseDto getNotificationByNotificationId(Long notificationId);
    NotificationResponseDto updateNotificationStatusToRead(Long notificationId);
    NotificationResponseDto updateNotificationStatusToDelete(Long notificationId);
    Long requestSubscription(SubscriptionApprovalRequestDto subscriptionApprovalRequestDto);
    SubscriptionApprovalResponseDto getSubscriptionApprovalBySubscriptionId(Long subscriptionId);
    SubscriptionApprovalKeyDto approveSubscriptionRequest(Long subscriptionApprovalId);
    SubscriptionApprovalKeyDto refuseSubscriptionRequest(Long subscriptionApprovalId);
    Long sendNotification(UUID senderKey, UUID receiverKey, String typeName);
}
