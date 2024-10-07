package com.fintech.pob.domain.account.service.transfer;

import com.fintech.pob.domain.account.dto.transfer.TransferCheckDTO;

public interface TransferChecker {
    TransferCheckResult check(TransferCheckDTO transferCheckDTO);
}
