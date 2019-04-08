package com.keybds.springblog.dto;

import javax.validation.constraints.Size;

public class AccountEmailDTO {

    private Long id;

    @Size(min = 6, max = 40, message = "please enter a value for email field between {min} and {max} characters")
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
