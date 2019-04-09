package com.keybds.springblog.dto;

import javax.validation.constraints.Size;

public class AccountSecurityDTO {

    private Long id;

    @Size(min = 6, max = 40, message = "please enter a value for new password field between {min} and {max} characters")
    private String newPassword;

    @Size(min = 6, max = 40, message = "please enter a value for confirm password field between {min} and {max} characters")
    private String confirmPassword;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
