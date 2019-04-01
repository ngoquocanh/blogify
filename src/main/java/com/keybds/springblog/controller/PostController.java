package com.keybds.springblog.controller;

import com.keybds.springblog.constants.UrlConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PostController {

    protected static final String VIEW_INDEX    = "index";

    @GetMapping(UrlConstants.HOME)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView(VIEW_INDEX);
        return mav;
    }
}
