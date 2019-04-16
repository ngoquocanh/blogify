package com.keybds.blogify.dto;

import javax.validation.constraints.Size;

public class AccountInfoDTO {

    private Long id;

    @Size(min = 2, max = 30, message = "please enter a value for first Name field between {min} and {max} characters")
    private String firstName;

    @Size(min = 4, max = 35, message = "please enter a value for last Name field between {min} and {max} characters")
    private String lastName;

    @Size(min = 6, max = 40, message = "please enter a value for username field between {min} and {max} characters")
    private String username;

    @Size(min = 6, max = 40, message = "please enter a value for email field between {min} and {max} characters")
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
