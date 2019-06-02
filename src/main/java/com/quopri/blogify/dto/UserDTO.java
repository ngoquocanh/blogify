package com.quopri.blogify.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class UserDTO implements Serializable {

    private Long id;

    @Size(min = 6, max = 40, message = "please enter a value for username field between {min} and {max} characters")
    private String username;

    @Size(min = 2, max = 30, message = "please enter a value for first Name field between {min} and {max} characters")
    private String firstName;

    @Size(min = 2, max = 30, message = "please enter a value for last Name field between {min} and {max} characters")
    private String lastName;

    @Size(min = 6, max = 40, message = "please enter a value for email field between {min} and {max} characters")
    private String email;

    @Size(min = 6, max = 40, message = "please enter a value for new password field between {min} and {max} characters")
    private String password;

    @Size(min = 6, max = 40, message = "please enter a value for confirm password field between {min} and {max} characters")
    private String confirmPassword;

    private Boolean enabled = false;

}
