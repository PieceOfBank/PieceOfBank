package com.fintech.pob.domain.notification.service;

import com.fintech.pob.domain.notification.dto.TransactionApprovalRequestDto;
import com.fintech.pob.domain.notification.dto.TransactionApprovalResponseDto;

public interface NotificationService {
    Long requestExceedTransfer(TransactionApprovalRequestDto transactionApprovalRequestDto);
    TransactionApprovalResponseDto acceptTransferRequest(Long transactionApprovalId);
}
