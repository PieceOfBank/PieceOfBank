package com.fintech.pob.domain.notification.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SubscriptionApprovalResponseDto {
    private UUID targetKey;
    private UUID protectKey;
}
