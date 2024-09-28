package com.fintech.pob.domain.account.controller;

import com.fintech.pob.domain.account.dto.client.*;
import com.fintech.pob.domain.account.dto.request.*;
import com.fintech.pob.domain.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account/client")
public class ClientAccountController {

    private final AccountService accountService;

    @PostMapping("/createDemandDepositAccount")
    public Mono<ResponseEntity<ClientAccountCreationResponseDTO>> createClientAccount(
            @RequestBody AccountCreationRequestDTO requestPayload) {
        return accountService.createAccount(requestPayload)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/inquireDemandDepositAccountList")
    public Mono<ResponseEntity<ClientAccountListResponseDTO>> getClientAccountList() {
        return accountService.getAccountList()
                .map(ResponseEntity::ok);
    }

    @PostMapping("/inquireDemandDepositAccount")
    public Mono<ResponseEntity<ClientAccountDetailResponseDTO>> getClientAccountDetail(
            @RequestBody AccountDetailRequestDTO requestPayload) {
        return accountService.getAccountDetail(requestPayload)
                .map(ResponseEntity::ok);
    }
}
