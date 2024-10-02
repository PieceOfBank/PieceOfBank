package com.fintech.pob.domain.account.dto.transfer;

import com.fintech.pob.domain.account.dto.client.ClientAccountDetailResponseDTO;
import com.fintech.pob.domain.account.dto.request.AccountTransferRequestDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferCheckDTO {
    private AccountTransferRequestDTO requestPayload;
    private ClientAccountDetailResponseDTO accountDeposit;
    private ClientAccountDetailResponseDTO accountWithdraw;

    public static TransferCheckDTO of(AccountTransferRequestDTO requestPayload, ClientAccountDetailResponseDTO accountDeposit, ClientAccountDetailResponseDTO accountWithdraw) {
        return TransferCheckDTO.builder()
                .requestPayload(requestPayload)
                .accountDeposit(accountDeposit)
                .accountWithdraw(accountWithdraw)
                .build();
    }
}
