package com.quopri.blogify.service.impl;

import com.quopri.blogify.configuration.ApplicationSettings;
import com.quopri.blogify.dto.AuthenticityTokenDTO;
import com.quopri.blogify.utils.CipherHelper;
import com.quopri.blogify.utils.JacksonJsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;

@Service(value = "smtpEmailService")
public class SmtpEmailServiceImpl extends AbstractEmailService {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private ApplicationSettings applicationSettings;

    @Override
    public void sendResetPasswordEmail(String resetPasswordUrl, String email, String token) throws Exception {
        AuthenticityTokenDTO authenticityToken = new AuthenticityTokenDTO(email, token);
        String strAuthenticityToken = JacksonJsonUtil.toJson(authenticityToken);
        String tokenEncrypted = CipherHelper.encrypt(strAuthenticityToken, applicationSettings.getSecretKeyPassword(),
                applicationSettings.getSecretKeySalt());
        tokenEncrypted = URLEncoder.encode(tokenEncrypted, CipherHelper.UTF8);
        String resetPasswordUrlToken = resetPasswordUrl + "/" + tokenEncrypted;

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(applicationSettings.getWebMasterEmail());
        mailMessage.setTo(email);
        mailMessage.setSubject("[Quopri] Reset your password");

        /** mail content **/
        StringBuilder message = new StringBuilder();
        message.append("We heard that you lost your password. Sorry about that!\r\n\r\n");
        message.append("But don’t worry! You can use the following link to reset your password:\r\n\r\n");
        message.append(resetPasswordUrlToken + "\r\n\r\n");
        message.append("If you don’t use this link within 2 hours, it will expire.\r\n\r\n");
        message.append("Thank you!");
        /** ----------------- **/

        mailMessage.setText(message.toString());
        sendGenericEmailMessage(mailMessage);
    }

    @Override
    public void sendGenericEmailMessage(SimpleMailMessage message) {
        mailSender.send(message);
    }
}
