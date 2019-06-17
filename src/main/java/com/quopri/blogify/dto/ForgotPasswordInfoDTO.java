package com.quopri.blogify.dto;

import java.io.Serializable;

public class ForgotPasswordInfoDTO implements Serializable {

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
