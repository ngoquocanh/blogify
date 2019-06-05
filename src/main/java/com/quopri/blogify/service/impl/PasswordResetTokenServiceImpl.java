package com.quopri.blogify.service.impl;

import com.quopri.blogify.entity.Account;
import com.quopri.blogify.entity.PasswordResetToken;
import com.quopri.blogify.repository.AccountRepository;
import com.quopri.blogify.repository.PasswordResetTokenRepository;
import com.quopri.blogify.service.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class PasswordResetTokenServiceImpl extends AbstractService implements PasswordResetTokenService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Value("${token.expiration.length.minutes}")
    private int tokenExpirationInMinutes;

    @Override
    @Transactional
    public PasswordResetToken createPasswordResetTokenForEmail(String email) {
        PasswordResetToken passwordResetToken = null;
        Account account =accountRepository.findByEmail(email);
        if (account != null) {
            String token = UUID.randomUUID().toString();
            LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
            passwordResetToken = new PasswordResetToken(token, account, now, tokenExpirationInMinutes);
            passwordResetToken = passwordResetTokenRepository.save(passwordResetToken);
        } else {
            // todo: log - Couldn't find a account for the given email
        }
        return passwordResetToken;
    }

    @Override
    public PasswordResetToken findByToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }
}
