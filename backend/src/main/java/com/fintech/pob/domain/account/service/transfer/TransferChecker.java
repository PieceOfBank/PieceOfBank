package com.fintech.pob.domain.account.service.transfer;

import com.fintech.pob.domain.account.dto.request.AccountTransferRequestDTO;
import com.fintech.pob.global.header.dto.HeaderRequestDTO;

public interface TransferChecker {
    TransferCheckResult check(AccountTransferRequestDTO requestPayload, HeaderRequestDTO header);
}
