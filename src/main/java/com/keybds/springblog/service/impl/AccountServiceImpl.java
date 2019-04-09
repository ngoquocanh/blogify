package com.keybds.springblog.service.impl;

import com.keybds.springblog.repository.AccountRepository;
import com.keybds.springblog.dto.AccountDTO;
import com.keybds.springblog.model.Account;
import com.keybds.springblog.model.AccountDetails;
import com.keybds.springblog.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service(value = "accountService")
public class AccountServiceImpl extends AbstractService implements AccountService  {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Need to check username and email existed or not
     * @param accountDTO
     * @return
     */
    @Override
    public Account createAccount(AccountDTO accountDTO) {
        Account account = new Account();
        account.setFirstName(accountDTO.getFirstName());
        account.setLastName(accountDTO.getLastName());
        account.setEmail(accountDTO.getEmail());
        account.setUsername(accountDTO.getUsername());
        account.setPassword(accountDTO.getPassword());
        Account accountAdded = accountRepository.save(account);
        return accountAdded;
    }

    @Override
    public Optional<Account> retrieveAccountById(Long id) {
        Optional<Account> accountFound = accountRepository.findById(id);
        return accountFound.isPresent() ? accountFound : Optional.empty();
    }

    @Override
    public Account updateAccountInfo(Account account) {
        Account accountToUpdate = new Account();
        if (accountRepository.existsById(account.getId())) {
            // info from input
            accountToUpdate.setId(account.getId());
            accountToUpdate.setFirstName(account.getFirstName());
            accountToUpdate.setLastName(account.getLastName());
            accountToUpdate.setUsername(account.getUsername());
            accountToUpdate.setEmail(account.getEmail());

            // for sure account existed
            Optional<Account> accountFound = retrieveAccountById(account.getId());
            accountToUpdate.setAuthorities(accountFound.get().getAuthorities());
            accountToUpdate.setPassword(accountFound.get().getPassword());
            accountToUpdate.setEnabled(accountFound.get().getEnabled());
            Account accountUpdated = accountRepository.save(accountToUpdate);
            return accountUpdated;
        } else {
            return account;
        }
    }

    @Override
    public Account updateAccountSecurity(Account account) {
        if (accountRepository.existsById(account.getId())) {
            // sure account existed
            Account accountExisted = retrieveAccountById(account.getId()).get();
            accountExisted.setPassword(account.getPassword());
            return accountRepository.save(accountExisted);
        } else {
            return account;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Account> getAllAccounts(Pageable pageable) {
        if (isPaged(pageable)) {
            return accountRepository.findAll(pageable);
        } else {
            return Page.empty();
        }
    }

    @Override
    public AccountDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if (!account.isEnabled())
            throw new DisabledException("Account is disabled");
        return new AccountDetails(account);
    }
}
