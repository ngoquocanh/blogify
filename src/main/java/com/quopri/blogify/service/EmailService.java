package com.quopri.blogify.service;

import com.quopri.blogify.dto.ContactUsDTO;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    /**
     * Send an email to user with reset link
     * @param resetPasswordUrl Url to reset password. E.g: /change-password
     * @param email            Email of user
     * @param token            Token created
     * @throws Exception
     */
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
