package com.fintech.pob.domain.notification.controller;

import com.fintech.pob.domain.notification.dto.NotificationRequestDto;
import com.fintech.pob.domain.notification.dto.TransactionApprovalRequestDto;
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

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/transfers/request")
    public ResponseEntity<Void> requestExceedTransfer(@RequestBody TransactionApprovalRequestDto transactionApprovalRequestDto) {
        notificationService.requestExceedTransfer(transactionApprovalRequestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/transfers/accept")
    public ResponseEntity<Void> acceptTransferRequest(@RequestBody TransactionApprovalRequestDto transactionApprovalRequestDto) {
        notificationService.acceptTransferRequest(transactionApprovalRequestDto);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/send")
    public Mono<ResponseEntity<Integer>> pushMessage(@RequestBody @Validated NotificationRequestDto notificationRequestDto) throws IOException {
        log.debug("[+] 푸시 메시지를 전송합니다.");
        // 비동기적으로 NotificationService의 sendMessageTo 메서드를 호출하고, Mono를 반환하도록 변경
        return notificationService.sendMessageTo(notificationRequestDto)
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK)) // 성공 시 OK 응답
                .doOnError(e -> log.error("푸시 메시지 전송 중 에러 발생: {}", e.getMessage())) // 에러 로그
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR))); // 실패 시 500 응답, 0 반환
    }
}