package com.quopri.blogify.service.impl;

import com.quopri.blogify.configuration.ApplicationSettings;
import com.quopri.blogify.dto.ContactUs;
import com.quopri.blogify.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;

public abstract class AbstractEmailService implements EmailService {

    @Autowired
    private ApplicationSettings applicationSettings;

    @Override
    public void sendFeedbackEmail(ContactUs contactUs) {
        sendGenericEmailMessage(prepareSimpleMailMessage(contactUs));
    }

    protected SimpleMailMessage prepareSimpleMailMessage(ContactUs contactUs) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(applicationSettings.getSendEmailContactUsTo());
        message.setFrom(contactUs.getEmail());
        message.setSubject("[Quopri - Contact Us]: From " + contactUs.getFullName());
        message.setText(contactUs.getMessage());
        return message;
    }
}
