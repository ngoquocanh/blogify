package com.quopri.blogify.controller;

import com.quopri.blogify.enums.StatusMessageCode;
import com.quopri.blogify.exceptions.MvcException;
import com.quopri.blogify.exceptions.PageNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalController {

    public static final String MODEL_ERROR_PAGE_TITLE_ATTRIBUTE     = "errorTitle";
    public static final String MODEL_ERROR_PAGE_CODE_ATTRIBUTE      = "errorCode";
    public static final String MODEL_ERROR_PAGE_MESSAGE_ATTRIBUTE   = "errorMessage";

    protected static final String VIEW_ERROR_UNKNOWN_PAGE  = "error/unknown";

    /**
     * Example:
     * ------------------------
     * Oops! An Error Occurred
     *
     * Error Detail:
     * Code: 9330
     * Message: System Error
     *
     * Back to the Home Page
     * ------------------------
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleUnknownException() {
        ModelAndView mav = new ModelAndView();
        mav.addObject(MODEL_ERROR_PAGE_TITLE_ATTRIBUTE, "Oops! An Error Occurred");
        mav.addObject(MODEL_ERROR_PAGE_CODE_ATTRIBUTE, StatusMessageCode.FAIL.getCode());
        mav.addObject(MODEL_ERROR_PAGE_MESSAGE_ATTRIBUTE, StatusMessageCode.FAIL.getMessage());
        mav.setViewName(VIEW_ERROR_UNKNOWN_PAGE);
        return mav;
    }

    @ExceptionHandler(MvcException.class)
    public ModelAndView handleMvcException(MvcException mvcException) {
        ModelAndView mav = new ModelAndView();
        mav.addObject(MODEL_ERROR_PAGE_TITLE_ATTRIBUTE, "Oops! An Error Occurred");
        mav.addObject(MODEL_ERROR_PAGE_CODE_ATTRIBUTE, mvcException.getCode());
        mav.addObject(MODEL_ERROR_PAGE_MESSAGE_ATTRIBUTE, mvcException.getMessage());
        mav.setViewName(VIEW_ERROR_UNKNOWN_PAGE);
        return mav;
    }

    @ExceptionHandler({DataAccessException.class, SQLException.class})
    public ModelAndView handleDataAccessException() {
        ModelAndView mav = new ModelAndView();
        mav.addObject(MODEL_ERROR_PAGE_TITLE_ATTRIBUTE, "Oops! An Error Occurred");
        mav.addObject(MODEL_ERROR_PAGE_CODE_ATTRIBUTE, StatusMessageCode.DATABASE_ERROR.getCode());
        mav.addObject(MODEL_ERROR_PAGE_MESSAGE_ATTRIBUTE, StatusMessageCode.DATABASE_ERROR.getMessage());
        mav.setViewName(VIEW_ERROR_UNKNOWN_PAGE);
        return mav;
    }

    @ExceptionHandler(PageNotFoundException.class)
    public ModelAndView handlePageNotFoundException() {
        ModelAndView mav = new ModelAndView();
        mav.addObject(MODEL_ERROR_PAGE_TITLE_ATTRIBUTE, "Oh No! Page Not Found");
        mav.addObject(MODEL_ERROR_PAGE_CODE_ATTRIBUTE, StatusMessageCode.PAGE_NOT_FOUND.getCode());
        mav.addObject(MODEL_ERROR_PAGE_MESSAGE_ATTRIBUTE, StatusMessageCode.PAGE_NOT_FOUND.getMessage());
        mav.setViewName(VIEW_ERROR_UNKNOWN_PAGE);
        return mav;
    }

}
