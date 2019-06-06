package com.quopri.blogify.controller;

import com.quopri.blogify.components.WebUI;
import com.quopri.blogify.configuration.ApplicationSettings;
import com.quopri.blogify.constants.UrlConstants;
import com.quopri.blogify.dto.ChangePasswordInfoDTO;
import com.quopri.blogify.dto.ResetPasswordInfoDTO;
import com.quopri.blogify.dto.UserDTO;
import com.quopri.blogify.entity.Account;
import com.quopri.blogify.entity.Authority;
import com.quopri.blogify.entity.PasswordResetToken;
import com.quopri.blogify.enums.RoleEnum;
import com.quopri.blogify.exceptions.MvcException;
import com.quopri.blogify.service.AccountService;
import com.quopri.blogify.service.EmailService;
import com.quopri.blogify.service.PasswordResetTokenService;
import com.quopri.blogify.utils.AccountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
public class AccountController extends BaseController {

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
    public static final String VIEW_RESET_PASSWORD  = "public/accounts/reset-password";
    public static final String VIEW_CHANGE_PASSWORD = "public/accounts/change-password";

    public static final String MODEL_ATTRIBUTE_USER = "user";
    public static final String MODEL_ATTRIBUTE_RESET_PASSWORD = "resetPasswordInfo";
    public static final String MODEL_ATTRIBUTE_CHANGE_PASSWORD = "changePasswordInfo";

    public static final String FEEDBACK_MESSAGE_KEY_SEND_EMAIL_RESET_PASSWORD_SUCCESS = "feedback.message.reset-password.success";
    public static final String ERROR_MESSAGE_KEY_RESET_PASSWORD = "error.message.reset-password";

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
        return mav;
    }

    /**
     * Url: /account/reset-password
     * Method: GET
     * @return The view reset password
     * @throws MvcException
     */
    @GetMapping(UrlConstants.ACCOUNT_RESET_PASSWORD)
    public ModelAndView openResetPasswordForm() throws MvcException {
        ModelAndView mav = new ModelAndView(VIEW_RESET_PASSWORD);
        mav.addObject(MODEL_ATTRIBUTE_RESET_PASSWORD, new ResetPasswordInfoDTO());
        return mav;
    }

    @PostMapping(UrlConstants.ACCOUNT_RESET_PASSWORD)
    public ModelAndView sendEmailResetPassword(HttpServletRequest request, @Valid @ModelAttribute(MODEL_ATTRIBUTE_RESET_PASSWORD) ResetPasswordInfoDTO resetPasswordInfo,
                                               BindingResult bindingResult, RedirectAttributes attributes) throws MvcException {
        ModelAndView mav = new ModelAndView(VIEW_RESET_PASSWORD);
        if (bindingResult.hasErrors()) {
            mav.addAllObjects(bindingResult.getModel());
            return mav;
        }

        PasswordResetToken passwordResetToken = passwordResetTokenService.createPasswordResetTokenForEmail(resetPasswordInfo.getEmail());
        if (passwordResetToken == null) {
            // todo: LOG - couldn't find a password reset token for email {}
            webUI.addErrorMessage(attributes, ERROR_MESSAGE_KEY_RESET_PASSWORD);
            mav.setViewName(redirectTo(UrlConstants.ACCOUNT_RESET_PASSWORD));
        } else {
            Account account = passwordResetToken.getAccount();
            String resetPasswordUrl = AccountUtil.createPasswordResetUrl(request, account.getEmail(), passwordResetToken.getToken());

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(applicationSettings.getWebMasterEmail());
            mailMessage.setTo(account.getEmail());
            mailMessage.setSubject("[Quopri] Reset your password");

            String message = "";

            mailMessage.setText(message + "\r\n\r\n" + resetPasswordUrl);
            emailService.sendGenericEmailMessage(mailMessage);
            webUI.addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_SEND_EMAIL_RESET_PASSWORD_SUCCESS);
            mav.setViewName(redirectTo(UrlConstants.ACCOUNT_RESET_PASSWORD));
        }
        return mav;
    }

    /**
     * Url: /account/change-password
     * Method: GET
     * @return The view change password
     * @throws MvcException
     */
    @GetMapping(UrlConstants.ACCOUNT_CHANGE_PASSWORD)
    public ModelAndView openChangePasswordForm(@RequestParam(value = "email", defaultValue = "") String email,
                                               @RequestParam(value = "token", defaultValue = "") String token) throws MvcException {
        ModelAndView mav = new ModelAndView(VIEW_CHANGE_PASSWORD);
        ChangePasswordInfoDTO changePasswordInfo = new ChangePasswordInfoDTO();
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(email)) {
            mav.addObject(WebUI.ERROR_MESSAGE_KEY, "Invalid email or token value");
            mav.setViewName(VIEW_CHANGE_PASSWORD);
            mav.addObject(MODEL_ATTRIBUTE_CHANGE_PASSWORD, changePasswordInfo);
        }

        PasswordResetToken passwordResetToken = passwordResetTokenService.findByToken(token);

        if (passwordResetToken == null) {
            mav.addObject(WebUI.ERROR_MESSAGE_KEY, "Invalid email or token value");
            mav.setViewName(VIEW_CHANGE_PASSWORD);
            mav.addObject(MODEL_ATTRIBUTE_CHANGE_PASSWORD, changePasswordInfo);
        }

        Account account = passwordResetToken.getAccount();
        if (!account.getEmail().equals(email)) {
            mav.addObject(WebUI.ERROR_MESSAGE_KEY, "Invalid email or token value");
            mav.setViewName(VIEW_CHANGE_PASSWORD);
            mav.addObject(MODEL_ATTRIBUTE_CHANGE_PASSWORD, changePasswordInfo);
        }

        // todo: check password expired
        return mav;
    }
}
