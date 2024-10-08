package com.fintech.pob.domain.subscriptionApproval.controller;

import com.fintech.pob.domain.subscriptionApproval.dto.SubscriptionApprovalKeyDto;
import com.fintech.pob.domain.subscriptionApproval.dto.SubscriptionApprovalRequestDto;
import com.fintech.pob.domain.subscriptionApproval.dto.SubscriptionApprovalResponseDto;
import com.fintech.pob.domain.subscriptionApproval.service.SubscriptionApprovalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/subscriptions")
public class SubscriptionApprovalController {

    private final SubscriptionApprovalService subscriptionApprovalService;

    public SubscriptionApprovalController(SubscriptionApprovalService subscriptionApprovalService) {
        this.subscriptionApprovalService = subscriptionApprovalService;
    }
    @PostMapping("/request")
    public ResponseEntity<Long> requestSubscription(@RequestBody SubscriptionApprovalRequestDto subscriptionApprovalRequestDto) {
        Long subscriptionApprovalId = subscriptionApprovalService.requestSubscription(subscriptionApprovalRequestDto);
        return ResponseEntity.ok(subscriptionApprovalId);
    }

    @GetMapping("/{subscriptionId}")
    public ResponseEntity<SubscriptionApprovalResponseDto> getSubscriptionApprovalBySubscriptionId(@PathVariable("subscriptionId") Long subscriptionId) {
        SubscriptionApprovalResponseDto subscriptionApprovalResponseDto = subscriptionApprovalService.getSubscriptionApprovalBySubscriptionId(subscriptionId);
        return ResponseEntity.ok(subscriptionApprovalResponseDto);
    }

    @PatchMapping("/approval")
    public ResponseEntity<SubscriptionApprovalKeyDto> approveSubscriptionRequest(@RequestBody Long subscriptionApprovalId) {
        SubscriptionApprovalKeyDto subscriptionApprovalKeyDto = subscriptionApprovalService.approveSubscriptionRequest(subscriptionApprovalId);
        return ResponseEntity.ok(subscriptionApprovalKeyDto);
    }

    @PatchMapping("/refusal")
    public ResponseEntity<SubscriptionApprovalKeyDto> refuseSubscriptionRequest(@RequestBody Long subscriptionApprovalId) {
        SubscriptionApprovalKeyDto subscriptionApprovalResponseDto = subscriptionApprovalService.refuseSubscriptionRequest(subscriptionApprovalId);
        return ResponseEntity.ok(subscriptionApprovalResponseDto);
    }
}
