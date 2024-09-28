package com.fintech.pob.domain.account.service;

import com.fintech.pob.domain.account.dto.client.*;
import com.fintech.pob.domain.account.dto.request.*;
import com.fintech.pob.global.header.dto.HeaderRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final WebClient webClient;
    private final HttpServletRequest request;

    public Mono<ClientAccountCreationResponseDTO> createAccount(AccountCreationRequestDTO requestPayload) {
        HeaderRequestDTO header = (HeaderRequestDTO) request.getAttribute("header");

        ClientAccountCreationRequestDTO requestDTO = new ClientAccountCreationRequestDTO();
        requestDTO.setHeader(header);
        requestDTO.setAccountTypeUniqueNo(requestPayload.getAccountTypeUniqueNo());

        return webClient.post()
                .uri("demandDeposit/createDemandDepositAccount")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(requestDTO)
                .retrieve()
                .bodyToMono(ClientAccountCreationResponseDTO.class);
    }

    public Mono<ClientAccountListResponseDTO> getAccountList() {
        HeaderRequestDTO header = (HeaderRequestDTO) request.getAttribute("header");

        ClientAccountListRequestDTO requestDTO = new ClientAccountListRequestDTO();
        requestDTO.setHeader(header);

        return webClient.post()
                .uri("demandDeposit/inquireDemandDepositAccountList")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(requestDTO)
                .retrieve()
                .bodyToMono(ClientAccountListResponseDTO.class);
    }

    public Mono<ClientAccountDetailResponseDTO> getAccountDetail(AccountDetailRequestDTO requestPayload) {
        HeaderRequestDTO header = (HeaderRequestDTO) request.getAttribute("header");

        ClientAccountDetailRequestDTO requestDTO = new ClientAccountDetailRequestDTO();
        requestDTO.setHeader(header);
        requestDTO.setAccountNo(requestPayload.getAccountNo());

        return webClient.post()
                .uri("demandDeposit/inquireDemandDepositAccount")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(requestDTO)
                .retrieve()
                .bodyToMono(ClientAccountDetailResponseDTO.class);
    }

    public Mono<ClientAccountTransferResponseDTO> updateAccountTransfer(AccountTransferRequestDTO requestPayload) {
        HeaderRequestDTO header = (HeaderRequestDTO) request.getAttribute("header");

        ClientAccountTransferRequestDTO requestDTO = ClientAccountTransferRequestDTO.of(header, requestPayload);

        return webClient.post()
                .uri("demandDeposit/updateDemandDepositAccountTransfer")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(requestDTO)
                .retrieve()
                .bodyToMono(ClientAccountTransferResponseDTO.class);
    }

    public Mono<ClientAccountHistoryListResponseDTO> getAccountHistoryList(AccountHistoryListRequestDTO requestPayload) {
        HeaderRequestDTO header = (HeaderRequestDTO) request.getAttribute("header");

        ClientAccountHistoryListRequestDTO requestDTO = ClientAccountHistoryListRequestDTO.of(header, requestPayload);

        return webClient.post()
                .uri("demandDeposit/inquireTransactionHistoryList")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(requestDTO)
                .retrieve()
                .bodyToMono(ClientAccountHistoryListResponseDTO.class);
    }

    public Mono<ClientAccountHistoryDetailResponseDTO> getAccountHistoryDetail(AccountHistoryDetailRequestDTO requestPayload) {
        HeaderRequestDTO header = (HeaderRequestDTO) request.getAttribute("header");

        ClientAccountHistoryDetailRequestDTO requestDTO = ClientAccountHistoryDetailRequestDTO.of(header, requestPayload);

        return webClient.post()
                .uri("demandDeposit/inquireTransactionHistory")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(requestDTO)
                .retrieve()
                .bodyToMono(ClientAccountHistoryDetailResponseDTO.class);
    }
}

