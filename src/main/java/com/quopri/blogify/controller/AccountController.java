package com.quopri.blogify.controller;

import com.quopri.blogify.constants.UrlConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccountController extends BaseController {

    public static final String VIEW_SIGN_IN     = "public/accounts/sign-in";

    @GetMapping(UrlConstants.SIGN_IN)
    public ModelAndView signIn() {
        ModelAndView mav = new ModelAndView();
        if (!isLoggedIn()) {
            mav.setViewName(VIEW_SIGN_IN);
        } else {
            mav.setViewName(redirectTo(UrlConstants.HOME));
        }
        return mav;
    }
}
