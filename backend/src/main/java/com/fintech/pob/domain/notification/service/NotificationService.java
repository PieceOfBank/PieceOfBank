package com.fintech.pob.domain.notification.service;

import com.fintech.pob.domain.notification.dto.NotificationRequestDto;
import com.fintech.pob.domain.notification.dto.TransactionApprovalRequestDto;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface NotificationService {
    Mono<Integer> sendMessageTo(NotificationRequestDto notificationSendDto) throws IOException;
    void requestExceedTransfer(TransactionApprovalRequestDto transactionApprovalRequestDto);
    void acceptTransferRequest(TransactionApprovalRequestDto transactionApprovalRequestDto);
}
