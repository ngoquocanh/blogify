package com.quopri.blogify.validator;

import com.quopri.blogify.dto.ForgotPasswordInfoDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.apache.commons.validator.routines.EmailValidator;

/**
 * This class uses to validate email to send reset password link to user
 * @author Anh Q. NGO (https://github.com/ngoquocanh)
 */
public class ForgotPasswordValidator implements Validator {

    private static final String EMAIL = "email";

    @Override
    public boolean supports(Class<?> clazz) {
        return ForgotPasswordInfoDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, EMAIL, "account.forgot-password.email.invalid.required");
        ForgotPasswordInfoDTO forgotPasswordInfo = (ForgotPasswordInfoDTO) target;
        if (!errors.hasErrors()) {
            if (!EmailValidator.getInstance().isValid(forgotPasswordInfo.getEmail())) {
                errors.rejectValue(EMAIL, "account.forgot-password.email.invalid.format");
            }
        }
    }
}
