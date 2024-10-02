package com.fintech.pob.domain.account.service.transfer;

import com.fintech.pob.domain.account.dto.transfer.TransferCheckDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferCheckService {
    private final List<TransferChecker> checkers;

    public TransferCheckResult checkTransfer(TransferCheckDTO transferCheckDTO) {
        for (TransferChecker checker : checkers) {
            TransferCheckResult result = checker.check(transferCheckDTO);
            if (result != TransferCheckResult.SUCCESS) {
                return result;
            }
        }
        return TransferCheckResult.SUCCESS;
    }
}
