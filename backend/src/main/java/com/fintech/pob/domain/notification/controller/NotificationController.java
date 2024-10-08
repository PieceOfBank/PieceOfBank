package com.fintech.pob.domain.notification.controller;

import com.fintech.pob.domain.notification.dto.expo.ExpoNotificationRequestDto;
import com.fintech.pob.domain.notification.dto.fcm.FCMRequestDto;
import com.fintech.pob.domain.notification.dto.NotificationResponseDto;
import com.fintech.pob.domain.subscriptionApproval.dto.SubscriptionApprovalKeyDto;
import com.fintech.pob.domain.subscriptionApproval.dto.SubscriptionApprovalRequestDto;
import com.fintech.pob.domain.subscriptionApproval.dto.SubscriptionApprovalResponseDto;
import com.fintech.pob.domain.notification.service.expo.ExpoService;
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
    private final ExpoService expoService;

    public NotificationController(NotificationService notificationService, FCMService fcmService, ExpoService expoService) {
        this.notificationService = notificationService;
        this.fcmService = fcmService;
        this.expoService = expoService;
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

    @PostMapping("/subscriptions/request")
    public ResponseEntity<Long> requestSubscription(@RequestBody SubscriptionApprovalRequestDto subscriptionApprovalRequestDto) {
        Long subscriptionApprovalId = notificationService.requestSubscription(subscriptionApprovalRequestDto);
        return ResponseEntity.ok(subscriptionApprovalId);
    }

    @GetMapping("subscriptions/{subscriptionId}")
    public ResponseEntity<SubscriptionApprovalResponseDto> getSubscriptionApprovalBySubscriptionId(@PathVariable("subscriptionId") Long subscriptionId) {
        SubscriptionApprovalResponseDto subscriptionApprovalResponseDto = notificationService.getSubscriptionApprovalBySubscriptionId(subscriptionId);
        return ResponseEntity.ok(subscriptionApprovalResponseDto);
    }

    @PatchMapping("/subscriptions/approval")
    public ResponseEntity<SubscriptionApprovalKeyDto> approveSubscriptionRequest(@RequestBody Long subscriptionApprovalId) {
        SubscriptionApprovalKeyDto subscriptionApprovalKeyDto = notificationService.approveSubscriptionRequest(subscriptionApprovalId);
        return ResponseEntity.ok(subscriptionApprovalKeyDto);
    }

    @PatchMapping("/subscriptions/refusal")
    public ResponseEntity<SubscriptionApprovalKeyDto> refuseSubscriptionRequest(@RequestBody Long subscriptionApprovalId) {
        SubscriptionApprovalKeyDto subscriptionApprovalResponseDto = notificationService.refuseSubscriptionRequest(subscriptionApprovalId);
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

    @PostMapping("/fcmMessage")
    public Mono<ResponseEntity<Integer>> pushFcmMessage(@RequestBody @Validated FCMRequestDto notificationRequestDto) throws IOException {
        log.debug("[+] 푸시 메시지를 전송합니다.");
        return fcmService.sendMessageTo(notificationRequestDto)
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .doOnError(e -> log.error("푸시 메시지 전송 중 에러 발생: {}", e.getMessage()))
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR))); // 실패 시 500 응답, 0 반환
    }

    @PostMapping("/expoMessage")
    public ResponseEntity<String> pushExpoMessage(@RequestBody @Validated ExpoNotificationRequestDto expoNotificationRequestDto) throws IOException {
        try {
            expoService.sendPushNotification(expoNotificationRequestDto);
            return ResponseEntity.ok("푸시 알림이 성공적으로 전송되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("푸시 알림 전송에 실패했습니다: " + e.getMessage());
        }
    }
}