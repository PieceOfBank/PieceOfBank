package com.fintech.pob.domain.notification.service;

import com.fintech.pob.domain.notification.dto.*;

import java.util.List;
import java.util.UUID;

public interface NotificationService {
    List<NotificationResponseDto> getAllNotificationsByReceiverKey(UUID receiverKey);
    NotificationResponseDto getNotificationByNotificationId(Long notificationId);
    NotificationResponseDto updateNotificationStatusToRead(Long notificationId);
    NotificationResponseDto updateNotificationStatusToDelete(Long notificationId);
    Long requestExceedTransfer(TransactionApprovalRequestDto transactionApprovalRequestDto);
    TransactionApprovalResponseDto approveTransferRequest(Long transactionApprovalId);
    TransactionApprovalResponseDto refuseTransferRequest(Long transactionApprovalId);
    TransactionApprovalResponseDto expireTransferRequest(Long transactionApprovalId);
    Long requestSubscription(SubscriptionApprovalRequestDto subscriptionApprovalRequestDto);
    SubscriptionApprovalResponseDto approveSubscriptionRequest(Long subscriptionApprovalId);
    SubscriptionApprovalResponseDto refuseSubscriptionRequest(Long subscriptionApprovalId);
}
