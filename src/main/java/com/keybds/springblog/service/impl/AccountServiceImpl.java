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
            accountToUpdate.setFirstName(account.getFirstName());
            accountToUpdate.setLastName(account.getLastName());
            accountToUpdate.setId(account.getId());

            // for sure account existed
            Optional<Account> accountFound = retrieveAccountById(account.getId());
            accountToUpdate.setEmail(accountFound.get().getEmail());
            accountToUpdate.setUsername(accountFound.get().getUsername());
            accountToUpdate.setPassword(accountFound.get().getPassword());
            accountToUpdate.setAuthorities(accountFound.get().getAuthorities());
            accountToUpdate.setEnabled(accountFound.get().getEnabled());

            Account accountUpdated = accountRepository.save(accountToUpdate);
            return accountUpdated;
        } else {
            return account;
        }
    }

    @Override
    public Account updateAccountUsername(AccountDTO accountDTO) {
        Account account = new Account();
        if (accountRepository.existsById(accountDTO.getId())) {
            // email from input
            account.setUsername(accountDTO.getUsername());

            account.setId(accountDTO.getId());

            // for sure account existed
            Optional<Account> accountFound = retrieveAccountById(accountDTO.getId());
            account.setFirstName(accountFound.get().getFirstName());
            account.setLastName(accountFound.get().getLastName());
            account.setEmail(accountFound.get().getEmail());
            account.setPassword(accountFound.get().getPassword());

            Account accountUpdated = accountRepository.save(account);
            return accountUpdated;
        } else {
            return account;
        }
    }

    @Override
    public Account updateAccountEmail(Account account) {
        Account accountToUpdate = new Account();
        if (accountRepository.existsById(account.getId())) {
            // username from input
            accountToUpdate.setEmail(account.getEmail());
            accountToUpdate.setId(account.getId());

            // sure account existed
            Optional<Account> accountFound = retrieveAccountById(account.getId());
            accountToUpdate.setFirstName(accountFound.get().getFirstName());
            accountToUpdate.setLastName(accountFound.get().getLastName());
            accountToUpdate.setUsername(accountFound.get().getUsername());
            accountToUpdate.setPassword(accountFound.get().getPassword());
            accountToUpdate.setAuthorities(accountFound.get().getAuthorities());
            accountToUpdate.setEnabled(accountFound.get().getEnabled());

            Account accountUpdated = accountRepository.save(accountToUpdate);
            return accountUpdated;
        } else {
            return account;
        }
    }

    @Override
    public Account updateAccountSecurity(AccountDTO accountDTO) {
        Account account = new Account();
        if (accountRepository.existsById(accountDTO.getId())) {
            // sure account existed
            Account accountExisted = retrieveAccountById(accountDTO.getId()).get();
            // check old password is same with user input or not
            if (accountDTO.getPassword().equals(accountExisted.getPassword())) {
                //set new password for account
                accountExisted.setPassword(accountDTO.getNewPassword());
                return accountRepository.save(accountExisted);
            } else {
                return account;
            }
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
