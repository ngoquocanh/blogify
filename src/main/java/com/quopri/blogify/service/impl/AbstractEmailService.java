package com.quopri.blogify.service.impl;

import com.quopri.blogify.configuration.ApplicationSettings;
import com.quopri.blogify.dto.ContactUsDTO;
import com.quopri.blogify.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;

public abstract class AbstractEmailService implements EmailService {

    @Autowired
    private ApplicationSettings applicationSettings;

    @Override
    public void sendFeedbackEmail(ContactUsDTO contactUs) {
        sendGenericEmailMessage(prepareSimpleMailMessage(contactUs));
    }

    protected SimpleMailMessage prepareSimpleMailMessage(ContactUsDTO contactUs) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(contactUs.getEmail());
        message.setTo(applicationSettings.getSendEmailContactUsTo());
        message.setSubject("[Quopri] Contact us");
        message.setText("From: " + contactUs.getFullName() + "<" + contactUs.getEmail() + ">\r\nContent: " + contactUs.getMessage());
        return message;
    }
}
