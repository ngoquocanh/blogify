package com.quopri.blogify.controller;

import com.quopri.blogify.components.WebUI;
import com.quopri.blogify.enums.StatusMessageCode;
import com.quopri.blogify.exceptions.MvcException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@ControllerAdvice
public class GlobalController {

    @Autowired
    private WebUI webUI;

    public static final String MODEL_ERROR_PAGE_TITLE_ATTRIBUTE     = "errorTitle";
    public static final String MODEL_ERROR_PAGE_MESSAGE_ATTRIBUTE   = "errorMessage";
    protected static final String VIEW_ERROR_UNKNOWN_PAGE  = "error/unknown";

    @ExceptionHandler(Exception.class)
    public ModelAndView handleUnknownException() {
        ModelAndView mav = new ModelAndView();
        mav.addObject(MODEL_ERROR_PAGE_TITLE_ATTRIBUTE, "Oops! Internal Server Error");
        mav.addObject(MODEL_ERROR_PAGE_MESSAGE_ATTRIBUTE, "Sorry... It's not you. It's us. Try to refresh this page or feel free to contact us if the problem persists.");
        webUI.addPageTitle(mav, "Internal Server Error");
        mav.setViewName(VIEW_ERROR_UNKNOWN_PAGE);
        return mav;
    }

    @ExceptionHandler(MvcException.class)
    public ModelAndView handleMvcException(HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        if (response.getStatus() == HttpStatus.OK.value()) {
            mav.addObject(MODEL_ERROR_PAGE_TITLE_ATTRIBUTE, "Oops! Not Found");
            mav.addObject(MODEL_ERROR_PAGE_MESSAGE_ATTRIBUTE, "Sorry, but we couldn't find the page you requested.");
            webUI.addPageTitle(mav, "Not Found");
            mav.setViewName(VIEW_ERROR_UNKNOWN_PAGE);
        } else {
            mav.addObject(MODEL_ERROR_PAGE_TITLE_ATTRIBUTE, "Oops! An Error Occurred");
            mav.addObject(MODEL_ERROR_PAGE_MESSAGE_ATTRIBUTE, "Something is not working right. In the meantime, try refreshing but if the problem persists feel free to contact us.");
            webUI.addPageTitle(mav, "An Error Occurred");
            mav.setViewName(VIEW_ERROR_UNKNOWN_PAGE);
        }
        return mav;
    }

    @ExceptionHandler({DataAccessException.class, SQLException.class})
    public ModelAndView handleDataAccessException() {
        ModelAndView mav = new ModelAndView();
        mav.addObject(MODEL_ERROR_PAGE_TITLE_ATTRIBUTE, "Oops! An Error Occurred");
        mav.addObject(MODEL_ERROR_PAGE_MESSAGE_ATTRIBUTE, StatusMessageCode.DATABASE_ERROR.getMessage());
        webUI.addPageTitle(mav, "Database Error");
        mav.setViewName(VIEW_ERROR_UNKNOWN_PAGE);
        return mav;
    }

}
