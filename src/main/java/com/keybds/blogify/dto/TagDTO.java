package com.keybds.blogify.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class TagDTO implements Serializable {

    private Long id;

    @NotEmpty(message = "Please enter value for name filed")
    @Length(min = 2, message = "Please enter value for name field greater than {min} characters")
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
