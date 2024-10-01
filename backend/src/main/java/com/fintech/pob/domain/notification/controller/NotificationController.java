package com.fintech.pob.domain.notification.controller;

import com.fintech.pob.domain.notification.dto.NotificationRequestDto;
import com.fintech.pob.domain.notification.dto.TransactionApprovalRequestDto;
import com.fintech.pob.domain.notification.dto.TransactionApprovalResponseDto;
import com.fintech.pob.domain.notification.service.FCMService;
import com.fintech.pob.domain.notification.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.IOException;

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


    @PostMapping("/send")
    public Mono<ResponseEntity<Integer>> pushMessage(@RequestBody @Validated NotificationRequestDto notificationRequestDto) throws IOException {
        log.debug("[+] 푸시 메시지를 전송합니다.");
        return fcmService.sendMessageTo(notificationRequestDto)
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .doOnError(e -> log.error("푸시 메시지 전송 중 에러 발생: {}", e.getMessage()))
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR))); // 실패 시 500 응답, 0 반환
    }
}