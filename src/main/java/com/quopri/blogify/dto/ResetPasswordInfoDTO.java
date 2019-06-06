package com.quopri.blogify.dto;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class ResetPasswordInfoDTO implements Serializable {

    @NotEmpty(message = "Please enter a valid email address.")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
