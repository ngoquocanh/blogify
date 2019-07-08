package com.quopri.blogify.controller;

import com.quopri.blogify.components.WebUI;
import com.quopri.blogify.configuration.ApplicationSettings;
import com.quopri.blogify.constants.UrlConstants;
import com.quopri.blogify.dto.AuthenticityTokenDTO;
import com.quopri.blogify.dto.ResetPasswordInfoDTO;
import com.quopri.blogify.dto.ForgotPasswordInfoDTO;
import com.quopri.blogify.dto.UserDTO;
import com.quopri.blogify.entity.Account;
import com.quopri.blogify.entity.AccountDetails;
import com.quopri.blogify.entity.Authority;
import com.quopri.blogify.entity.PasswordResetToken;
import com.quopri.blogify.enums.ResetPasswordResult;
import com.quopri.blogify.enums.RoleEnum;
import com.quopri.blogify.exceptions.MvcException;
import com.quopri.blogify.service.AccountService;
import com.quopri.blogify.service.EmailService;
import com.quopri.blogify.service.PasswordResetTokenService;
import com.quopri.blogify.service.impl.AbstractService;
import com.quopri.blogify.utils.AccountUtil;
import com.quopri.blogify.validator.ForgotPasswordValidator;
import com.quopri.blogify.validator.ResetPasswordValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Controller
public class AccountController extends BaseController {

    /** Application logger **/
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private WebUI webUI;

    @Autowired
    @Qualifier(value = "accountService")
    private AccountService accountService;

    @Autowired
    @Qualifier("smtpEmailService")
    private EmailService emailService;

    @Autowired
    private ApplicationSettings applicationSettings;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    public static final String VIEW_SIGN_IN         = "public/accounts/sign-in";
    public static final String VIEW_SIGN_UP         = "public/accounts/sign-up";
    public static final String VIEW_FORGOT_PASSWORD = "public/accounts/forgot-password";
    public static final String VIEW_RESET_PASSWORD  = "public/accounts/reset-password";

    public static final String MODEL_ATTRIBUTE_USER = "user";
    public static final String MODEL_ATTRIBUTE_FORGOT_PASSWORD = "forgotPasswordInfo";
    public static final String MODEL_ATTRIBUTE_RESET_PASSWORD  = "resetPasswordInfo";
    public static final String MODEL_ATTRIBUTE_TOKEN = "token";

    public static final String FEEDBACK_MESSAGE_KEY_SEND_EMAIL_RESET_PASSWORD_SUCCESS = "feedback.message.reset-password.email-success";
    public static final String FEEDBACK_MESSAGE_KEY_PASSWORD_RESET_SUCCESS            = "feedback.message.reset-password.success";
    public static final String ERROR_RESET_PASSWORD_INVALID_EMAIL = "error.message.reset-password.invalid-email";
    public static final String ERROR_RESET_PASSWORD_INVALID_OR_EXPIRED_LINK = "error.message.reset-password.invalid-or-expired-link";

    @GetMapping(UrlConstants.SIGN_IN)
    public ModelAndView openSignInForm() {
        ModelAndView mav = new ModelAndView();
        if (!isLoggedIn()) {
            mav.setViewName(VIEW_SIGN_IN);
            webUI.addPageTitle(mav, "Login");
        } else {
            mav.setViewName(redirectTo(UrlConstants.HOME));
        }
        return mav;
    }

    @GetMapping(UrlConstants.SIGN_UP)
    public ModelAndView openSignUpForm(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView(VIEW_SIGN_UP);
        if (request.getUserPrincipal() != null) {
            mav.setViewName(redirectTo(UrlConstants.HOME));
        } else {
            mav.setViewName(VIEW_SIGN_UP);
            UserDTO userDTO = new UserDTO();
            mav.addObject(MODEL_ATTRIBUTE_USER, userDTO);
        }
        return mav;
    }

    @PostMapping(UrlConstants.SIGN_UP)
    public ModelAndView signUp(@Valid @ModelAttribute(MODEL_ATTRIBUTE_USER) UserDTO userDTO, BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView(VIEW_SIGN_UP);
        if (bindingResult.hasErrors())
            return mav;

        if (applicationSettings.getCanSignUp()) {
            if (applicationSettings.getMailSignUp())
                userDTO.setEnabled(false);
            else
                userDTO.setEnabled(true);

            if (accountService.isExistedUsername(userDTO.getUsername())) {
                bindingResult.rejectValue("username", "account.register.duplicate-username");
                return mav;
            }

            if (accountService.isExistedEmail(userDTO.getEmail())) {
                bindingResult.rejectValue("email", "account.register.duplicate-email");
                return mav;
            }

            if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
                bindingResult.rejectValue("confirmPassword", "account.register.password-mismatch");
                return mav;
            }

            Account account = new Account();
            account.setUsername(userDTO.getUsername());
            account.setFirstName(userDTO.getFirstName());
            account.setLastName(userDTO.getLastName());
            account.setEmail(userDTO.getEmail());
            account.setEnabled(userDTO.getEnabled());
            account.setPassword(passwordEncoder.encode(userDTO.getPassword()));

            Authority authority = new Authority(RoleEnum.USER);
            Set<Authority> authorities = new HashSet<>();
            authorities.add(authority);

            account.setAuthorities(authorities);

            accountService.createAccount(account);
            mav.setViewName("public/accounts/sign-up-success");
        } else {
            mav.addObject(WebUI.FEEDBACK_MESSAGE_KEY, "Sorry! Sign up function not available right now.");
            mav.setViewName(VIEW_SIGN_UP);
        }
        return mav;
    }

    /**
     * Url: /account/forgot-password
     * Method: GET
     * @return The view forgot password
     * @throws MvcException
     */
    @GetMapping(UrlConstants.ACCOUNT_FORGOT_PASSWORD)
    public ModelAndView openForgotPasswordForm() throws MvcException {
        ModelAndView mav = new ModelAndView(VIEW_FORGOT_PASSWORD);
        mav.addObject(MODEL_ATTRIBUTE_FORGOT_PASSWORD, new ForgotPasswordInfoDTO());
        return mav;
    }

    @InitBinder(MODEL_ATTRIBUTE_FORGOT_PASSWORD)
    public void initForgotPasswordValidator(WebDataBinder dataBinder) {
        dataBinder.setValidator(new ForgotPasswordValidator());
    }

    /**
     * Url: /account/forgot-password
     * Method: POST
     * @param forgotPasswordInfo
     * @param bindingResult
     * @param attributes
     * @return
     * @throws MvcException
     */
    @PostMapping(UrlConstants.ACCOUNT_FORGOT_PASSWORD)
    public ModelAndView sendEmailForgotPassword(@Validated @ModelAttribute(MODEL_ATTRIBUTE_FORGOT_PASSWORD) ForgotPasswordInfoDTO forgotPasswordInfo,
                                               BindingResult bindingResult, RedirectAttributes attributes) throws MvcException, Exception {
        ModelAndView mav = new ModelAndView(VIEW_FORGOT_PASSWORD);
        if (bindingResult.hasErrors()) {
            mav.addAllObjects(bindingResult.getModel());
            return mav;
        }
        PasswordResetToken passwordResetToken = passwordResetTokenService.createPasswordResetTokenForEmail(forgotPasswordInfo.getEmail());
        if (passwordResetToken == null) {
            webUI.addErrorMessage(attributes, ERROR_RESET_PASSWORD_INVALID_EMAIL);
            mav.setViewName(redirectTo(UrlConstants.ACCOUNT_FORGOT_PASSWORD));
            logger.info("Couldn't find a password reset token for email {}", forgotPasswordInfo.getEmail());
        } else {
            Account account = passwordResetToken.getAccount();
            emailService.sendResetPasswordEmail(getUrlContextPath(UrlConstants.ACCOUNT_RESET_PASSWORD),
                    account.getEmail(), passwordResetToken.getToken());
            webUI.addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_SEND_EMAIL_RESET_PASSWORD_SUCCESS);
            mav.setViewName(redirectTo(UrlConstants.ACCOUNT_FORGOT_PASSWORD));
            logger.info("Forgot password successfully", forgotPasswordInfo.getEmail());
        }
        return mav;
    }

    /**
     * Url: /account/reset-password
     * Method: GET
     * @return The view change password
     * @throws MvcException
     */
    @GetMapping(UrlConstants.ACCOUNT_RESET_PASSWORD)
    public ModelAndView openChangePasswordFormPreLogin() throws MvcException {
        ModelAndView mav = new ModelAndView(VIEW_RESET_PASSWORD);
        if (isLoggedIn()) {
            AccountDetails accountDetails = getLoggedInInfo();
            ResetPasswordInfoDTO resetPasswordInfo = new ResetPasswordInfoDTO();
            resetPasswordInfo.setEmail(accountDetails.getAccount().getEmail());
            resetPasswordInfo.setVerificationToken(AbstractService.AUTHORIZED_CODE);
            mav.addObject(MODEL_ATTRIBUTE_RESET_PASSWORD, resetPasswordInfo);
        } else {
            mav.setViewName(redirectTo(UrlConstants.ACCOUNT_RESET_PASSWORD));
        }
        return mav;
    }

    /**
     * Url: /account/reset-password/{token}
     * Method: GET
     * @return The view change password
     * @throws MvcException
     */
    @GetMapping(UrlConstants.ACCOUNT_RESET_PASSWORD_TOKEN)
    public ModelAndView openChangePasswordForm(@PathVariable(MODEL_ATTRIBUTE_TOKEN) String token) throws MvcException {
        ModelAndView mav = new ModelAndView(VIEW_RESET_PASSWORD);

        // check invalid token value
        if (token == null || StringUtils.isEmpty(token)) {
            mav.setViewName(VIEW_FORGOT_PASSWORD);
            mav.addObject(WebUI.ERROR_MESSAGE_KEY, webUI.getMessage(ERROR_RESET_PASSWORD_INVALID_OR_EXPIRED_LINK));
            mav.addObject(MODEL_ATTRIBUTE_FORGOT_PASSWORD, new ForgotPasswordInfoDTO());
            logger.info("Invalid token value");
            return mav;
        }

        // decrypt token
        AuthenticityTokenDTO authenticityToken = null;
        try {
            authenticityToken = AccountUtil.extractResetToken(token, applicationSettings.getSecretKeyPassword());
        } catch (Exception e) {
            // do nothing
        }

        if (authenticityToken == null) {
            mav.setViewName(VIEW_FORGOT_PASSWORD);
            mav.addObject(WebUI.ERROR_MESSAGE_KEY, webUI.getMessage(ERROR_RESET_PASSWORD_INVALID_OR_EXPIRED_LINK));
            mav.addObject(MODEL_ATTRIBUTE_FORGOT_PASSWORD, new ForgotPasswordInfoDTO());
            logger.info("Can not extract token");
            return mav;
        } else {
            PasswordResetToken passwordResetToken = passwordResetTokenService.findByToken(authenticityToken.getToken());
            if (passwordResetToken == null) {
                mav.setViewName(VIEW_FORGOT_PASSWORD);
                mav.addObject(WebUI.ERROR_MESSAGE_KEY, webUI.getMessage(ERROR_RESET_PASSWORD_INVALID_OR_EXPIRED_LINK));
                mav.addObject(MODEL_ATTRIBUTE_FORGOT_PASSWORD, new ForgotPasswordInfoDTO());
                logger.info("The token could not be found");
                return mav;
            }
            Account account = passwordResetToken.getAccount();
            if (!account.getEmail().equals(authenticityToken.getEmail())) {
                mav.setViewName(VIEW_FORGOT_PASSWORD);
                mav.addObject(WebUI.ERROR_MESSAGE_KEY, webUI.getMessage(ERROR_RESET_PASSWORD_INVALID_OR_EXPIRED_LINK));
                mav.addObject(MODEL_ATTRIBUTE_FORGOT_PASSWORD, new ForgotPasswordInfoDTO());
                logger.info("The email passed as parameter does not match the email associated with the token");
            }

            if (LocalDateTime.now(Clock.systemUTC()).isAfter(passwordResetToken.getExpiryDate())) {
                mav.setViewName(VIEW_FORGOT_PASSWORD);
                mav.addObject(WebUI.ERROR_MESSAGE_KEY, webUI.getMessage(ERROR_RESET_PASSWORD_INVALID_OR_EXPIRED_LINK));
                mav.addObject(MODEL_ATTRIBUTE_FORGOT_PASSWORD, new ForgotPasswordInfoDTO());
                logger.info("The token has expired");
                return mav;
            }

            ResetPasswordInfoDTO changePasswordInfo = new ResetPasswordInfoDTO();
            changePasswordInfo.setEmail(account.getEmail());
            changePasswordInfo.setVerificationToken(passwordResetToken.getToken());
            mav.setViewName(VIEW_RESET_PASSWORD);
            mav.addObject(MODEL_ATTRIBUTE_RESET_PASSWORD, changePasswordInfo);
            return mav;
        }
    }

    @InitBinder(MODEL_ATTRIBUTE_RESET_PASSWORD)
    public void initResetPasswordValidator(WebDataBinder dataBinder) {
        dataBinder.setValidator(new ResetPasswordValidator());
    }

    /**
     * Url: /account/reset-password
     * Method: POST
     * @param changePasswordInfo
     * @param bindingResult
     * @param attributes
     * @return
     * @throws MvcException
     */
    @PostMapping(UrlConstants.ACCOUNT_RESET_PASSWORD)
    public ModelAndView changePassword(@Validated @ModelAttribute(MODEL_ATTRIBUTE_RESET_PASSWORD) ResetPasswordInfoDTO changePasswordInfo,
                                       BindingResult bindingResult, RedirectAttributes attributes) throws MvcException {
        ModelAndView mav = new ModelAndView(VIEW_RESET_PASSWORD);
        if (bindingResult.hasErrors()) {
            mav.addObject(MODEL_ATTRIBUTE_RESET_PASSWORD, changePasswordInfo);
            mav.setViewName(VIEW_RESET_PASSWORD);
            return mav;
        }
        ResetPasswordResult resetPasswordResult = accountService.updatePassword(changePasswordInfo);
        switch (resetPasswordResult) {
            case ERROR:
                webUI.addErrorMessage(attributes, ERROR_RESET_PASSWORD_INVALID_OR_EXPIRED_LINK);
                mav.setViewName(redirectTo(UrlConstants.ACCOUNT_RESET_PASSWORD));
                break;
            case CHANGE_PASSWORD_SUCCESS:
                webUI.addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_PASSWORD_RESET_SUCCESS);
                mav.setViewName(redirectTo(UrlConstants.SIGN_IN));
                break;
        }
        return mav;
    }
}
