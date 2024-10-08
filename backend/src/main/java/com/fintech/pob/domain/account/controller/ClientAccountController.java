package com.fintech.pob.domain.account.controller;

import com.fintech.pob.domain.account.dto.client.*;
import com.fintech.pob.domain.account.dto.request.*;
import com.fintech.pob.domain.account.service.account.AccountService;
import com.fintech.pob.domain.user.entity.User;
import com.fintech.pob.domain.user.service.LocalUserService;
import com.fintech.pob.global.auth.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account/client")
public class ClientAccountController {

    private final AccountService accountService;
    private final JwtUtil jwtUtil;
    private final LocalUserService localuserService;

    @PostMapping("/createDemandDepositAccount")
    public Mono<ResponseEntity<ClientAccountCreationResponseDTO>> createClientAccount(
            @RequestBody AccountCreationRequestDTO requestPayload) {
        return accountService.createAccount(requestPayload)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/inquireDemandDepositAccountList")
    public Mono<ResponseEntity<ClientAccountListResponseDTO>> getClientAccountList(HttpServletRequest request) {

        String token = request.getHeader("Authorization");

        String userKeyString;
        try {
            userKeyString = jwtUtil.extractUserKey(token);
        } catch (Exception e) {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));
        }

        if (userKeyString == null) {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));
        }

        User user = localuserService.findByUserKey(userKeyString);
        String accountNo = user.getAccountNo();


        return accountService.getAccountList()
                .map(accountListResponse -> {
                    List<ClientAccountListResponseDTO.Record> sortedAccountList;

                    if (accountNo != null && !accountNo.isEmpty()) {
                        sortedAccountList = accountListResponse.getRec().stream()
                                .sorted((record1, record2) -> {
                                    if (record1.getAccountNo().equals(accountNo)) {
                                        return -1;
                                    } else if (record2.getAccountNo().equals(accountNo)) {
                                        return 1;
                                    }
                                    return 0;
                                })
                                .collect(Collectors.toList());
                    } else {
                        sortedAccountList = accountListResponse.getRec();
                    }

                    ClientAccountListResponseDTO sortedResponse = new ClientAccountListResponseDTO();
                    sortedResponse.setHeader(accountListResponse.getHeader());
                    sortedResponse.setRec(sortedAccountList);
                    return ResponseEntity.ok(sortedResponse);
                });
    }

    @PostMapping("/inquireDemandDepositAccount")
    public Mono<ResponseEntity<ClientAccountDetailResponseDTO>> getClientAccountDetail(
            @RequestBody AccountDetailRequestDTO requestPayload) {
        return accountService.getAccountDetail(requestPayload)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/updateDemandDepositAccountTransfer")
    public Mono<ResponseEntity<ClientAccountTransferResponseDTO>> updateClientAccountTransfer(
            @RequestBody AccountTransferRequestDTO requestPayload) {
        return accountService.updateAccountTransfer(requestPayload)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/inquireTransactionHistoryList")
    public Mono<ResponseEntity<ClientAccountHistoryListResponseDTO>> getClientAccountHistoryList(
            @RequestBody AccountHistoryListRequestDTO requestPayload) {
        return accountService.getAccountHistoryList(requestPayload)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/inquireTransactionHistory")
    public Mono<ResponseEntity<ClientAccountHistoryDetailResponseDTO>> getClientAccountHistoryDetail(
            @RequestBody AccountHistoryDetailRequestDTO requestPayload) {
        return accountService.getAccountHistoryDetail(requestPayload)
                .map(ResponseEntity::ok);
    }

    @PatchMapping("/setPrimaryAccount")
    public ResponseEntity<String> setPrimaryAccount(@RequestBody AccountUpdateRequestDTO request) {
        try {
            UUID userKey = UUID.fromString(request.getUserKey());
            localuserService.updateAccountNo(userKey, request.getAccountNo());
            return ResponseEntity.ok("대표 계좌 업데이트 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("대표 계좌 업데이트 실패: " + e.getMessage());
        }
    }

}
