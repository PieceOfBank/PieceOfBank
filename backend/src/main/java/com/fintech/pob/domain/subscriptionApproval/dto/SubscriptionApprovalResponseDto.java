package com.fintech.pob.domain.subscriptionApproval.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SubscriptionApprovalResponseDto {
    private String requesterName;
}
