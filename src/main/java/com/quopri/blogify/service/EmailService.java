package com.quopri.blogify.service;

import com.quopri.blogify.dto.ContactUsDTO;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendResetPasswordEmail(String resetPasswordUrl, String email, String token) throws Exception;

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
