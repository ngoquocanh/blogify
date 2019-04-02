package com.keybds.springblog.service;

import com.keybds.springblog.dto.AccountDTO;
import com.keybds.springblog.model.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface AccountService extends UserDetailsService {
    Account createAccount(AccountDTO accountDTO);
    Optional<Account> retrieveAccountById(Long id);
    Account updateAccountInfo(AccountDTO accountDTO);
    Account updateAccountUsername(AccountDTO accountDTO);
    Account updateAccountEmail(AccountDTO accountDTO);
    Account updateAccountSecurity(AccountDTO accountDTO);
}
