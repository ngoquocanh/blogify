package com.quopri.blogify.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@Controller
public class CustomErrorController implements ErrorController {

    public static final String MODEL_ERROR_PAGE_TITLE_ATTRIBUTE     = "errorTitle";
    public static final String MODEL_ERROR_PAGE_MESSAGE_ATTRIBUTE   = "errorMessage";
    protected static final String VIEW_ERROR_UNKNOWN_PAGE  = "error/unknown";

    @GetMapping("/error")
    public ModelAndView handleError(HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        if (response.getStatus() == HttpStatus.NOT_FOUND.value()) {
            mav.addObject(MODEL_ERROR_PAGE_TITLE_ATTRIBUTE, "Oops! Not Found");
            mav.addObject(MODEL_ERROR_PAGE_MESSAGE_ATTRIBUTE, "Sorry, but we couldn't find the page you requested.");
            mav.setViewName(VIEW_ERROR_UNKNOWN_PAGE);
        } else {
            mav.addObject(MODEL_ERROR_PAGE_TITLE_ATTRIBUTE, "Oops! Unknown Error");
            mav.addObject(MODEL_ERROR_PAGE_MESSAGE_ATTRIBUTE, "Something is not working right.");
            mav.setViewName(VIEW_ERROR_UNKNOWN_PAGE);
        }
        return mav;
    }

    @Override
    public String getErrorPath() {
        return "error/unknown";
    }
}
