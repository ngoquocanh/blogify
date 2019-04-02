package com.keybds.springblog.dto;

import javax.validation.constraints.Size;

public class AccountDTO {
    private Long id;

    @Size(min = 6, max = 40, message = "please enter a value for username field between {min} and {max} characters")
    private String username;

    @Size(min = 6, max = 40, message = "please enter a value for email field between {min} and {max} characters")
    private String email;

    @Size(min = 2, max = 30, message = "please enter a value for first Name field between {min} and {max} characters")
    private String firstName;

    @Size(min = 4, max = 35, message = "please enter a value for last Name field between {min} and {max} characters")
    private String lastName;

    @Size(min = 6, max = 40, message = "please enter a value for password field between {min} and {max} characters")
    private String password; // currentPassword

    @Size(min = 6, max = 40, message = "please enter a value for new password field between {min} and {max} characters")
    private String newPassword;

    @Size(min = 6, max = 40, message = "please enter a value for confirm password field between {min} and {max} characters")
    private String confirmPassword;

    private boolean enabled;

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
