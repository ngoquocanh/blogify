package com.quopri.blogify.controller;

import com.quopri.blogify.components.WebUI;
import com.quopri.blogify.constants.UrlConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccountController extends BaseController {

    @Autowired
    private WebUI webUI;

    public static final String VIEW_SIGN_IN     = "public/accounts/sign-in";

    @GetMapping(UrlConstants.SIGN_IN)
    public ModelAndView signIn() {
        ModelAndView mav = new ModelAndView();
        if (!isLoggedIn()) {
            mav.setViewName(VIEW_SIGN_IN);
            webUI.addPageTitle(mav, "Login");
        } else {
            mav.setViewName(redirectTo(UrlConstants.HOME));
        }
        return mav;
    }
}
