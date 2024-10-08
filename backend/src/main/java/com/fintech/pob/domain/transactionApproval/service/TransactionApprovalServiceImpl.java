package com.fintech.pob.domain.transactionApproval.service;

import com.fintech.pob.domain.notification.entity.Notification;
import com.fintech.pob.domain.notification.entity.NotificationStatus;
import com.fintech.pob.domain.notification.entity.NotificationType;
import com.fintech.pob.domain.notification.repository.notification.NotificationRepository;
import com.fintech.pob.domain.notification.repository.notification.NotificationTypeRepository;
import com.fintech.pob.domain.notification.repository.transaction.TransactionApprovalRepository;
import com.fintech.pob.domain.transactionApproval.dto.TransactionApprovalRequestDto;
import com.fintech.pob.domain.transactionApproval.dto.TransactionApprovalResponseDto;
import com.fintech.pob.domain.transactionApproval.entity.TransactionApproval;
import com.fintech.pob.domain.transactionApproval.entity.TransactionApprovalStatus;
import com.fintech.pob.domain.user.entity.User;
import com.fintech.pob.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Component
@Service
@RequiredArgsConstructor
public class TransactionApprovalServiceImpl implements TransactionApprovalService {

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationTypeRepository notificationTypeRepository;
    private final TransactionApprovalRepository transactionApprovalRepository;

    @Override
    @Transactional
    public Long requestTransfer(TransactionApprovalRequestDto transactionApprovalRequestDto, String typeName) {
        NotificationType notificationType;
        if (typeName.equals("한도 초과 알림")) {
            notificationType = notificationTypeRepository.findByTypeName("한도 초과 알림")
                    .orElseThrow(() -> new IllegalArgumentException("한도 초과 알림 타입이 없습니다."));
        } else if (typeName.equals("계좌 비활성 알림")) {
            notificationType = notificationTypeRepository.findByTypeName("계좌 비활성 알림")
                    .orElseThrow(() -> new IllegalArgumentException("계좌 비활성 알림 타입이 없습니다."));
        }
        else {
            throw new IllegalArgumentException("잘못된 알림 타입입니다: " + typeName);
        }

        User sender = userRepository.findByUserKey(transactionApprovalRequestDto.getSenderKey())
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));
        User receiver = userRepository.findByUserKey(transactionApprovalRequestDto.getReceiverKey())
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));

        // Notification 생성
        Notification notification = Notification.builder()
                .senderUser(sender)
                .receiverUser(receiver)
                .type(notificationType)
                .created(LocalDateTime.now())
                .notificationStatus(NotificationStatus.UNREAD)
                .build();
        notificationRepository.save(notification);

        // TransactionApproval 생성
        TransactionApproval transactionApproval = TransactionApproval.builder()
                .notification(notification)
                .receiverName(transactionApprovalRequestDto.getReceiverName())
                .amount(transactionApprovalRequestDto.getAmount())
                .transactionApprovalStatus(TransactionApprovalStatus.PENDING)
                .build();
        TransactionApproval savedApproval = transactionApprovalRepository.save(transactionApproval);

        // 푸시 알림 전송 처리 예정
        return savedApproval.getTransactionApprovalId();
    }

    @Override
    @Transactional
    public TransactionApprovalResponseDto approveTransferRequest(Long transactionApprovalId) {
        TransactionApproval transactionApproval = transactionApprovalRepository.findById(transactionApprovalId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction Approval not found"));
        transactionApproval.setStatus(TransactionApprovalStatus.APPROVED);
        transactionApprovalRepository.save(transactionApproval);

        Notification notification = transactionApproval.getNotification();
        notification.setNotificationStatus(NotificationStatus.READ); // 읽음 처리
        notificationRepository.save(notification);

        return TransactionApprovalResponseDto.builder()
                .senderKey(notification.getSenderUser().getUserKey())
                .receiverKey(notification.getReceiverUser().getUserKey())
                .receiverName(transactionApproval.getReceiverName())
                .amount(transactionApproval.getAmount())
                .status(transactionApproval.getStatus())
                .build();
    }

    @Override
    public TransactionApprovalResponseDto refuseTransferRequest(Long transactionApprovalId) {
        TransactionApproval transactionApproval = transactionApprovalRepository.findById(transactionApprovalId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction Refusal not found"));
        transactionApproval.setStatus(TransactionApprovalStatus.REFUSED);
        transactionApprovalRepository.save(transactionApproval);

        Notification notification = transactionApproval.getNotification();

        return TransactionApprovalResponseDto.builder()
                .senderKey(notification.getSenderUser().getUserKey())
                .receiverKey(notification.getReceiverUser().getUserKey())
                .receiverName(transactionApproval.getReceiverName())
                .amount(transactionApproval.getAmount())
                .status(transactionApproval.getStatus())
                .build();
    }

    @Override
    public TransactionApprovalResponseDto expireTransferRequest(Long transactionApprovalId) {
        TransactionApproval transactionApproval = transactionApprovalRepository.findById(transactionApprovalId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction Expiry not found"));
        transactionApproval.setStatus(TransactionApprovalStatus.EXPIRED);
        transactionApprovalRepository.save(transactionApproval);

        Notification notification = transactionApproval.getNotification();

        return TransactionApprovalResponseDto.builder()
                .senderKey(notification.getSenderUser().getUserKey())
                .receiverKey(notification.getReceiverUser().getUserKey())
                .receiverName(transactionApproval.getReceiverName())
                .amount(transactionApproval.getAmount())
                .status(transactionApproval.getStatus())
                .build();
    }
}
