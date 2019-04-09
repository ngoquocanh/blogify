package com.keybds.springblog.dto;

import javax.validation.constraints.Size;

public class AccountUsernameDTO {

    private Long id;

    @Size(min = 6, max = 40, message = "please enter a value for username field between {min} and {max} characters")
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
