package com.fintech.pob.domain.notification.service.notification;

import com.fintech.pob.domain.notification.dto.notification.NotificationResponseDto;
import com.fintech.pob.domain.notification.dto.subscription.SubscriptionApprovalKeyDto;
import com.fintech.pob.domain.notification.dto.subscription.SubscriptionApprovalRequestDto;
import com.fintech.pob.domain.notification.dto.subscription.SubscriptionApprovalResponseDto;
import com.fintech.pob.domain.notification.dto.transaction.TransactionApprovalRequestDto;
import com.fintech.pob.domain.notification.dto.transaction.TransactionApprovalResponseDto;

import java.util.List;
import java.util.UUID;

public interface NotificationService {
    List<NotificationResponseDto> getAllNotificationsByReceiverKey(UUID receiverKey);
    NotificationResponseDto getNotificationByNotificationId(Long notificationId);
    NotificationResponseDto updateNotificationStatusToRead(Long notificationId);
    NotificationResponseDto updateNotificationStatusToDelete(Long notificationId);
    Long requestTransfer(TransactionApprovalRequestDto transactionApprovalRequestDto, String typeName);
    TransactionApprovalResponseDto approveTransferRequest(Long transactionApprovalId);
    TransactionApprovalResponseDto refuseTransferRequest(Long transactionApprovalId);
    TransactionApprovalResponseDto expireTransferRequest(Long transactionApprovalId);
    Long requestSubscription(SubscriptionApprovalRequestDto subscriptionApprovalRequestDto);
    SubscriptionApprovalResponseDto getSubscriptionApprovalBySubscriptionId(Long subscriptionId);
    SubscriptionApprovalKeyDto approveSubscriptionRequest(Long subscriptionApprovalId);
    SubscriptionApprovalKeyDto refuseSubscriptionRequest(Long subscriptionApprovalId);
    Long sendNotification(UUID senderKey, UUID receiverKey, String typeName);
}
