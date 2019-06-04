package com.quopri.blogify.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


public class ContactUsDTO implements Serializable {

    @NotEmpty(message = "Please enter value for full name filed")
    @Length(min = 2, message = "Please enter value for full name field greater than or equal {min} characters")
    private String fullName;

    @NotEmpty(message = "Please enter value for email filed")
    @Length(min = 2, message = "Please enter value for email field greater than or equal {min} characters")
    private String email;

    @NotEmpty(message = "Please enter value for message filed")
    @Length(max = 2000, message = "please enter value for message field less than or equal {max} characters")
    private String message;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
