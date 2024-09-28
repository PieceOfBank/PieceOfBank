package com.fintech.pob.domain.account.service;

import com.fintech.pob.domain.account.dto.client.ClientAccountCreationRequestDTO;
import com.fintech.pob.domain.account.dto.client.ClientAccountCreationResponseDTO;
import com.fintech.pob.domain.account.dto.client.ClientAccountListRequestDTO;
import com.fintech.pob.domain.account.dto.client.ClientAccountListResponseDTO;
import com.fintech.pob.global.header.dto.HeaderRequestDTO;
import com.fintech.pob.global.header.service.HeaderService;
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

    public Mono<ClientAccountCreationResponseDTO> createAccount(String accountTypeUniqueNo) {
        HeaderRequestDTO header = (HeaderRequestDTO) request.getAttribute("header");

        ClientAccountCreationRequestDTO requestDTO = new ClientAccountCreationRequestDTO();
        requestDTO.setHeader(header);
        requestDTO.setAccountTypeUniqueNo(accountTypeUniqueNo);

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
}

