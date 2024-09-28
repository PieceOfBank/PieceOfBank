package com.fintech.pob.domain.notification.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * 거래 이체 시 받을 객체
 *
 */
@Slf4j
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionApprovalRequestDto {
    private UUID senderKey; // 피보호자
    private UUID receiverKey; // 보호자
    private String receiverName; // 이체를 받는 타인
    private Long amount; // 이체 금액
}
