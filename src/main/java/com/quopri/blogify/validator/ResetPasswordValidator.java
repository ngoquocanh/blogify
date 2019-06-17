package com.quopri.blogify.validator;

import com.quopri.blogify.dto.ResetPasswordInfoDTO;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * This class uses to validate password before update to database
 * @author Anh Q. NGO (https://github.com/ngoquocanh)
 */
public class ResetPasswordValidator implements Validator {

    private static final String NEW_PASSWORD = "newPassword";
    private static final String CONFIRM_PASSWORD = "confirmPassword";

    @Override
    public boolean supports(Class<?> clazz) {
        return ResetPasswordInfoDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ResetPasswordInfoDTO resetPasswordInfo = (ResetPasswordInfoDTO) target;
        if (StringUtils.isEmpty(resetPasswordInfo.getNewPassword().trim()) || resetPasswordInfo.getNewPassword() == null) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, NEW_PASSWORD, "account.reset-password.new-password.invalid.required");
        } else {
            if (resetPasswordInfo.getNewPassword().length() < 8) {
                errors.rejectValue(NEW_PASSWORD, "account.reset-password.new-password.invalid.length");
            } else {
                if (resetPasswordInfo.getNewPassword() != null && resetPasswordInfo.getConfirmPassword() != null
                        && !resetPasswordInfo.getNewPassword().equals(resetPasswordInfo.getConfirmPassword())) {
                    errors.rejectValue(CONFIRM_PASSWORD, "account.reset-password.confirm-password.invalid.mismatch");
                }
            }
        }
    }
}
