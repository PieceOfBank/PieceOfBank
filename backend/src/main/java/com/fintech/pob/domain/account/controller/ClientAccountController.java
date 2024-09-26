package com.fintech.pob.domain.account.controller;

import com.fintech.pob.domain.account.dto.client.ClientAccountCreationResponseDTO;
import com.fintech.pob.domain.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account/client")
public class ClientAccountController {

    private final AccountService accountService;

    @PostMapping("/createDemandDepositAccount")
    public Mono<ResponseEntity<ClientAccountCreationResponseDTO>> createClientAccount(
            @RequestParam String userKey,
            @RequestParam String accountTypeUniqueNo) {
        return accountService.createAccount(userKey, accountTypeUniqueNo)
                .map(ResponseEntity::ok);
    }
}
