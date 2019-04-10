package com.keybds.springblog.service;

import com.keybds.springblog.dto.AccountDTO;
import com.keybds.springblog.exceptions.MvcException;
import com.keybds.springblog.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface AccountService extends UserDetailsService {
    Account createAccount(AccountDTO accountDTO);
    Optional<Account> retrieveAccountById(Long id);
    Account updateAccountInfo(Account account);
    Account updateAccountSecurity(Account account);

    /**
     * get all accounts for pagination
     * @param pageable
     * @return
     */
    Page<Account> getAllAccounts(Pageable pageable);

    /**
     * delete multi accounts
     * @param accountIds
     * @throws MvcException
     */
    void deleteAccounts(List<Long> accountIds) throws MvcException;
}
