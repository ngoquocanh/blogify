package com.quopri.blogify.controller;

import com.quopri.blogify.enums.StatusMessageCode;
import com.quopri.blogify.exceptions.MvcException;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalController {

    public static final String MODEL_ERROR_PAGE_TITLE_ATTRIBUTE     = "errorTitle";
    public static final String MODEL_ERROR_PAGE_MESSAGE_ATTRIBUTE   = "errorMessage";
    protected static final String VIEW_ERROR_UNKNOWN_PAGE  = "error/unknown";

    @ExceptionHandler(Exception.class)
    public ModelAndView handleUnknownException() {
        ModelAndView mav = new ModelAndView();
        mav.addObject(MODEL_ERROR_PAGE_TITLE_ATTRIBUTE, "Oops! Internal Server Error");
        mav.addObject(MODEL_ERROR_PAGE_MESSAGE_ATTRIBUTE, "Sorry... It's not you. It's us. Try to refresh this page or feel free to contact us if the problem persists.");
        mav.setViewName(VIEW_ERROR_UNKNOWN_PAGE);
        return mav;
    }

    @ExceptionHandler(MvcException.class)
    public ModelAndView handleMvcException(MvcException mvcException) {
        ModelAndView mav = new ModelAndView();
        mav.addObject(MODEL_ERROR_PAGE_TITLE_ATTRIBUTE, "Oops! An Error Occurred");
        mav.addObject(MODEL_ERROR_PAGE_MESSAGE_ATTRIBUTE, "Something is not working right. In the meantime, try refreshing but if the problem persists feel free to contact us.");
        mav.setViewName(VIEW_ERROR_UNKNOWN_PAGE);
        return mav;
    }

    @ExceptionHandler({DataAccessException.class, SQLException.class})
    public ModelAndView handleDataAccessException() {
        ModelAndView mav = new ModelAndView();
        mav.addObject(MODEL_ERROR_PAGE_TITLE_ATTRIBUTE, "Oops! An Error Occurred");
        mav.addObject(MODEL_ERROR_PAGE_MESSAGE_ATTRIBUTE, StatusMessageCode.DATABASE_ERROR.getMessage());
        mav.setViewName(VIEW_ERROR_UNKNOWN_PAGE);
        return mav;
    }

}
