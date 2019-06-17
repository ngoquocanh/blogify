package com.quopri.blogify.service.impl;

import com.quopri.blogify.dto.ResetPasswordInfoDTO;
import com.quopri.blogify.entity.PasswordResetToken;
import com.quopri.blogify.enums.ResetPasswordResult;
import com.quopri.blogify.exceptions.MvcException;
import com.quopri.blogify.repository.AccountRepository;
import com.quopri.blogify.dto.AccountDTO;
import com.quopri.blogify.entity.Account;
import com.quopri.blogify.entity.AccountDetails;
import com.quopri.blogify.repository.PasswordResetTokenRepository;
import com.quopri.blogify.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service(value = "accountService")
public class AccountServiceImpl extends AbstractService implements AccountService  {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Optional<Account> retrieveAccountById(Long id) {
        Optional<Account> accountFound = accountRepository.findById(id);
        return accountFound.isPresent() ? accountFound : Optional.empty();
    }

    @Override
    public Account updateAccountInfo(Account account) {
        Account accountToUpdate;
        if (accountRepository.existsById(account.getId())) {
            // info from input include: id, firstName, lastName, username, email
            accountToUpdate = account;

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
            accountExisted.setPassword(passwordEncoder.encode(account.getPassword()));
            return accountRepository.save(accountExisted);
        } else {
            return account;
        }
    }

    @Override
    public void updatePassword(Long id, String password) {
        password = passwordEncoder.encode(password);
        accountRepository.updatePassword(id, password);
    }

    @Override
    public ResetPasswordResult updatePassword(ResetPasswordInfoDTO changePasswordInfo) {
        if (!changePasswordInfo.getVerificationToken().equals(AUTHORIZED_CODE)) {
            PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(changePasswordInfo.getVerificationToken());

            if (passwordResetToken == null) {
                // todo: LOG - The token could not be found
                return ResetPasswordResult.ERROR;
            }

            Account account = passwordResetToken.getAccount();

            if (!account.getEmail().equals(changePasswordInfo.getEmail())) {
                // todo: LOG - The email passed as parameter does not match the email associated with the token
                return ResetPasswordResult.ERROR;
            }

            if (LocalDateTime.now(Clock.systemUTC()).isAfter(passwordResetToken.getExpiryDate())) {
                // todo: LOG - The token has expired
                passwordResetTokenRepository.delete(passwordResetToken);
                return ResetPasswordResult.ERROR;
            }
            updatePassword(account.getId(), changePasswordInfo.getNewPassword());
            passwordResetTokenRepository.delete(passwordResetToken);
        } else {
            Account account = accountRepository.findByEmail(changePasswordInfo.getEmail());
            updatePassword(account.getId(), changePasswordInfo.getNewPassword());
        }
        return ResetPasswordResult.CHANGE_PASSWORD_SUCCESS;
    }

    @Override
    public Account loadAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
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
    public void deleteAccounts(List<Long> accountIds) throws MvcException {
        for (Long acId : accountIds) {
            if (accountRepository.existsById(acId)) {
                accountRepository.deleteById(acId);
            }
        }
    }

    @Override
    public boolean isExistedUsername(String username) {
        return (accountRepository.findByUsername(username) != null) ? true : false;
    }

    @Override
    public boolean isExistedEmail(String email) {
        return (accountRepository.findByEmail(email) != null) ? true : false;
    }

    @Override
    public AccountDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if (!account.isEnabled())
            throw new DisabledException("Account is disabled");
        return new AccountDetails(account);
    }
}
