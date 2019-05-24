package com.quopri.blogify.controller;

import com.quopri.blogify.components.WebUI;
import com.quopri.blogify.configuration.ApplicationSettings;
import com.quopri.blogify.constants.UrlConstants;
import com.quopri.blogify.dto.UserDTO;
import com.quopri.blogify.entity.Account;
import com.quopri.blogify.entity.Authority;
import com.quopri.blogify.enums.RoleEnum;
import com.quopri.blogify.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

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
    private ApplicationSettings applicationSettings;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static final String VIEW_SIGN_IN         = "public/accounts/sign-in";
    public static final String VIEW_SIGN_UP         = "public/accounts/sign-up";

    public static final String MODEL_ATTRIBUTE_USER = "user";

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

        return mav;
    }
}
