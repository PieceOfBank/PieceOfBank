package com.fintech.pob.domain.notification.service;

import com.fintech.pob.domain.notification.dto.TransactionApprovalRequestDto;
import com.fintech.pob.domain.notification.dto.TransactionApprovalResponseDto;

public interface NotificationService {
    Long requestExceedTransfer(TransactionApprovalRequestDto transactionApprovalRequestDto);
    TransactionApprovalResponseDto approveTransferRequest(Long transactionApprovalId);
    TransactionApprovalResponseDto refuseTransferRequest(Long transactionApprovalId);
    TransactionApprovalResponseDto expireTransferRequest(Long transactionApprovalId);
}
