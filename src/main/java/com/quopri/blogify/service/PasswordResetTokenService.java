package com.quopri.blogify.service;

import com.quopri.blogify.entity.PasswordResetToken;

public interface PasswordResetTokenService {

    /**
     * Creates a new Password Reset Token for the user identified by the given email
     * @param email The email uniquely identifying the user
     * @return a new Password Reset Token for the user identified by the given email or null if none was found
     */
    PasswordResetToken createPasswordResetTokenForEmail(String email);

    /**
     * Retrieves a Password Reset Token for the given token id.
     * @param token The token to be returned
     * @return A Password Reset Token if one was found or null if none was found.
     */
    PasswordResetToken findByToken(String token);
}
