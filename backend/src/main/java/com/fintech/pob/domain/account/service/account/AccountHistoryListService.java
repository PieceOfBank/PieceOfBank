package com.fintech.pob.domain.account.service.account;

import com.fintech.pob.domain.account.dto.client.ClientAccountHistoryListRequestDTO;
import com.fintech.pob.domain.account.dto.client.ClientAccountHistoryListResponseDTO;
import com.fintech.pob.domain.account.dto.request.AccountHistoryListRequestDTO;
import com.fintech.pob.global.header.dto.HeaderRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AccountHistoryListService {

    private final WebClient webClient;
    private final HttpServletRequest request;

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
}