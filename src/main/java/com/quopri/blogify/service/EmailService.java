package com.quopri.blogify.service;

import com.quopri.blogify.dto.ContactUsDTO;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    /**
     * Sends an email with the content in the ContactUS dto
     * @param contactUs
     */
    void sendFeedbackEmail(ContactUsDTO contactUs);

    /**
     * Sends an email with the content of the Simple Mail Message object.
     * @param message
     */
    void sendGenericEmailMessage(SimpleMailMessage message);
}
