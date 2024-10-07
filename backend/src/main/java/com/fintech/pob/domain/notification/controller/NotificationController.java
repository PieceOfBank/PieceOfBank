package com.fintech.pob.domain.notification.controller;

import com.fintech.pob.domain.notification.dto.fcm.FCMRequestDto;
import com.fintech.pob.domain.notification.dto.notification.NotificationResponseDto;
import com.fintech.pob.domain.notification.dto.subscription.SubscriptionApprovalRequestDto;
import com.fintech.pob.domain.notification.dto.subscription.SubscriptionApprovalResponseDto;
import com.fintech.pob.domain.notification.dto.transaction.TransactionApprovalRequestDto;
import com.fintech.pob.domain.notification.dto.transaction.TransactionApprovalResponseDto;
import com.fintech.pob.domain.notification.service.fcm.FCMService;
import com.fintech.pob.domain.notification.service.notification.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;
    private final FCMService fcmService;

    public NotificationController(NotificationService notificationService, FCMService fcmService) {
        this.notificationService = notificationService;
        this.fcmService = fcmService;
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponseDto>> getNotificationsByReceiverKey(@RequestParam("receiverKey") UUID receiverKey) {
        List<NotificationResponseDto> notifications = notificationService.getAllNotificationsByReceiverKey(receiverKey);
        if (notifications.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<NotificationResponseDto> getNotificationById(@PathVariable("notificationId") Long notificationId) {
        NotificationResponseDto notification = notificationService.getNotificationByNotificationId(notificationId);
        return ResponseEntity.ok(notification);
    }

    @PatchMapping("/read/{notificationId}")
    public ResponseEntity<NotificationResponseDto> updateNotificationStatusToRead(@PathVariable("notificationId") Long notificationId) {
        NotificationResponseDto updatedNotification = notificationService.updateNotificationStatusToRead(notificationId);
        return ResponseEntity.ok(updatedNotification);
    }

    @PatchMapping("/delete/{notificationId}")
    public ResponseEntity<NotificationResponseDto> updateNotificationStatusToDelete(@PathVariable("notificationId") Long notificationId) {
        NotificationResponseDto updatedNotification = notificationService.updateNotificationStatusToDelete(notificationId);
        return ResponseEntity.ok(updatedNotification);
    }

    @PostMapping("/transfers/request")
    public ResponseEntity<Long> requestExceedTransfer(@RequestBody TransactionApprovalRequestDto transactionApprovalRequestDto) {
        Long transactionApprovalId = notificationService.requestExceedTransfer(transactionApprovalRequestDto);
        return ResponseEntity.ok(transactionApprovalId);
    }

    @PatchMapping("/transfers/approval")
    public ResponseEntity<TransactionApprovalResponseDto> approveTransferRequest(@RequestBody Long transactionApprovalId) {
        TransactionApprovalResponseDto transactionApprovalResponseDto = notificationService.approveTransferRequest(transactionApprovalId);
        return ResponseEntity.ok(transactionApprovalResponseDto);
    }

    @PatchMapping("/transfers/refusal")
    public ResponseEntity<TransactionApprovalResponseDto> refuseTransferRequest(@RequestBody Long transactionApprovalId) {
        TransactionApprovalResponseDto transactionApprovalResponseDto = notificationService.refuseTransferRequest(transactionApprovalId);
        return ResponseEntity.ok(transactionApprovalResponseDto);
    }

    @PatchMapping("/transfers/expiry")
    public ResponseEntity<TransactionApprovalResponseDto> expireTransferRequest(@RequestBody Long transactionApprovalId) {
        TransactionApprovalResponseDto transactionApprovalResponseDto = notificationService.expireTransferRequest(transactionApprovalId);
        return ResponseEntity.ok(transactionApprovalResponseDto);
    }

    @PostMapping("/subscriptions/request")
    public ResponseEntity<Long> requestSubscription(@RequestBody SubscriptionApprovalRequestDto subscriptionApprovalRequestDto) {
        Long subscriptionApprovalId = notificationService.requestSubscription(subscriptionApprovalRequestDto);
        return ResponseEntity.ok(subscriptionApprovalId);
    }

    @PatchMapping("/subscriptions/approval")
    public ResponseEntity<SubscriptionApprovalResponseDto> approveSubscriptionRequest(@RequestBody Long subscriptionApprovalId) {
        SubscriptionApprovalResponseDto subscriptionApprovalResponseDto = notificationService.approveSubscriptionRequest(subscriptionApprovalId);
        return ResponseEntity.ok(subscriptionApprovalResponseDto);
    }

    @PatchMapping("/subscriptions/refusal")
    public ResponseEntity<SubscriptionApprovalResponseDto> refuseSubscriptionRequest(@RequestBody Long subscriptionApprovalId) {
        SubscriptionApprovalResponseDto subscriptionApprovalResponseDto = notificationService.refuseSubscriptionRequest(subscriptionApprovalId);
        return ResponseEntity.ok(subscriptionApprovalResponseDto);
    }

    @PostMapping()
    public ResponseEntity<Long> sendNotification(
            @RequestParam("senderKey") UUID senderKey,
            @RequestParam("receiverKey") UUID receiverKey,
            @RequestParam("typeName") String typeName) {
        Long notificationId = notificationService.sendNotification(senderKey, receiverKey, typeName);
        return ResponseEntity.ok(notificationId);
    }

    @PostMapping("/message")
    public Mono<ResponseEntity<Integer>> pushMessage(@RequestBody @Validated FCMRequestDto notificationRequestDto) throws IOException {
        log.debug("[+] 푸시 메시지를 전송합니다.");
        return fcmService.sendMessageTo(notificationRequestDto)
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .doOnError(e -> log.error("푸시 메시지 전송 중 에러 발생: {}", e.getMessage()))
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR))); // 실패 시 500 응답, 0 반환
    }
}