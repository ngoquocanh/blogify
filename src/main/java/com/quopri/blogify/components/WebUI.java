package com.quopri.blogify.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Component
public class WebUI {

    @Autowired
    private MessageSource messageSource;

    public static final String FEEDBACK_MESSAGE_KEY = "feedbackMessage";

    public String getMessage(String code, Object... params) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, params, currentLocale);
    }

    public void addFeedbackMessage(RedirectAttributes redirectAttributes, String code, Object... params) {
        String localizedFeedbackMessage = getMessage(code, params);
        redirectAttributes.addFlashAttribute(FEEDBACK_MESSAGE_KEY, localizedFeedbackMessage);
    }
}
