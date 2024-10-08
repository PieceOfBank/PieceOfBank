package com.fintech.pob.domain.account.service.account;

import com.fintech.pob.domain.account.entity.Account;
import com.fintech.pob.domain.account.repository.AccountRepository;
import com.fintech.pob.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountClientService {

    private final AccountRepository accountRepository;

    public Account saveAccount(User user, String accountNo) {
        Account account = new Account();
        account.setUser(user);
        account.setAccountNo(accountNo);
        return accountRepository.save(account);
    }

    public Account findByUser(User user) {
        return accountRepository.findByUser(user);
    }

    public Account findByAccountNo(String accountNo) {
        return accountRepository.findByAccountNo(accountNo);
    }
}
