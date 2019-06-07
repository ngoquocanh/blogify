package com.quopri.blogify.dto;

import javax.validation.constraints.Size;
import java.io.Serializable;

public class ChangePasswordInfoDTO implements Serializable {

    @Size(min = 8, message = "please enter a value for password field at least {min} characters")
    private String newPassword;

    @Size(min = 8, message = "please enter a value for password field at least {min} characters")
    private String confirmPassword;

    private String email;
    private String verificationToken;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
