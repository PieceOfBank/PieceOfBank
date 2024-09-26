package com.fintech.pob.domain.account.service;

import com.fintech.pob.domain.account.dto.client.ClientAccountCreationRequestDTO;
import com.fintech.pob.domain.account.dto.client.ClientAccountCreationResponseDTO;
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

    public Mono<ClientAccountCreationResponseDTO> createAccount(String userKey, String accountTypeUniqueNo) {
        HeaderRequestDTO header = (HeaderRequestDTO) request.getAttribute("header");

        ClientAccountCreationRequestDTO requestDTO = new ClientAccountCreationRequestDTO();
        requestDTO.setHeader(header);
        requestDTO.setAccountTypeUniqueNo(accountTypeUniqueNo);

        System.out.println(header);
        System.out.println(accountTypeUniqueNo);

        return webClient.post()
                .uri("demandDeposit/createDemandDepositAccount")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(requestDTO)
                .retrieve()
                .bodyToMono(ClientAccountCreationResponseDTO.class);
    }
}

