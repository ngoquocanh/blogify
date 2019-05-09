package com.quopri.blogify.validator;

import com.quopri.blogify.dto.AccountDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AccountValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return AccountDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "validator.accounts.firstName");
    }
}
