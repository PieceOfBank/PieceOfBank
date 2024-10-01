package com.fintech.pob.domain.account.service.transfer;

import com.fintech.pob.domain.account.dto.request.AccountTransferRequestDTO;
import com.fintech.pob.global.header.dto.HeaderRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferCheckService {
    private final List<TransferChecker> checkers;

    public TransferCheckResult checkTransfer(AccountTransferRequestDTO requestPayload, HeaderRequestDTO header) {
        for (TransferChecker checker : checkers) {
            TransferCheckResult result = checker.check(requestPayload, header);
            if (result != TransferCheckResult.SUCCESS) {
                return result;
            }
        }
        return TransferCheckResult.SUCCESS;
    }
}
