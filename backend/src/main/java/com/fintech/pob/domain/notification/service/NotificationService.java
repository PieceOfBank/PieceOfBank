package com.fintech.pob.domain.notification.service;

import com.fintech.pob.domain.notification.dto.NotificationRequestDto;
import com.fintech.pob.domain.notification.dto.TransactionApprovalRequestDto;
import com.fintech.pob.domain.notification.dto.TransactionApprovalResponseDto;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface NotificationService {
    Mono<Integer> sendMessageTo(NotificationRequestDto notificationSendDto) throws IOException;
    Long requestExceedTransfer(TransactionApprovalRequestDto transactionApprovalRequestDto);
    TransactionApprovalResponseDto acceptTransferRequest(Long transactionApprovalId);
}
