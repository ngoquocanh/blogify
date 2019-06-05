package com.quopri.blogify.service;

import com.quopri.blogify.entity.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
public class PasswordResetTokenTests {

    @Value("${token.expiration.length.minutes}")
    private int expirationTimeInMinutes;

    @Autowired
    @Qualifier(value = "accountService")
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Before
    public void init() {
        Assert.assertTrue(expirationTimeInMinutes == 120);
        Assert.assertNotNull(passwordEncoder);
        Assert.assertNotNull(accountService);
    }

    private static final String TEST_EMAIL = "anhngoquoc@gmail.com";

//    @Test
//    public void createPasswordResetTokenForEmail() {
//        Account account = accountRepository.findByEmail(TEST_EMAIL);
//        PasswordResetToken passwordResetToken = passwordResetTokenService.createPasswordResetTokenForEmail(account.getEmail());
//        Assert.assertNotNull(passwordResetToken);
//    }

    @Test
    public void updatePassword() throws Exception {
        String password = passwordEncoder.encode("1234567");
        Account account = accountService.loadAccountByEmail(TEST_EMAIL);
        //accountService.updatePassword(account.getId(), password);
    }
}
