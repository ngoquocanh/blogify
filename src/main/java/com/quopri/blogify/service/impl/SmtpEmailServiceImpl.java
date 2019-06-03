package com.quopri.blogify.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service(value = "smtpEmailService")
public class SmtpEmailServiceImpl extends AbstractEmailService {

    @Autowired
    private MailSender mailSender;

    @Override
    public void sendGenericEmailMessage(SimpleMailMessage message) {
        mailSender.send(message);
    }
}
