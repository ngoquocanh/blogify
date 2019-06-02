package com.quopri.blogify.components;

import com.quopri.blogify.configuration.ApplicationSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Component
public class WebUI {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ApplicationSettings applicationSettings;

    public static final String FEEDBACK_MESSAGE_KEY = "feedbackMessage";
    public static final String PAGE_TITLE = "pageTitle";

    public String getMessage(String code, Object... params) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, params, currentLocale);
    }

    public void addFeedbackMessage(RedirectAttributes redirectAttributes, String code, Object... params) {
        String localizedFeedbackMessage = getMessage(code, params);
        redirectAttributes.addFlashAttribute(FEEDBACK_MESSAGE_KEY, localizedFeedbackMessage);
    }

    public ModelAndView addPageTitle(ModelAndView mav) {
        return addPageTitle(mav, null);
    }

    public ModelAndView addPageTitle(ModelAndView mav, String message) {
        return message == null ? mav.addObject(PAGE_TITLE, applicationSettings.getTitle()) :
                mav.addObject(PAGE_TITLE, applicationSettings.getTitle().concat(" - ").concat(message));
    }
}
