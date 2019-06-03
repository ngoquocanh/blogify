package com.quopri.blogify.controller.stranger;

import com.quopri.blogify.constants.UrlConstants;
import com.quopri.blogify.dto.ContactUs;
import com.quopri.blogify.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContactUsController {

    public static final String MODEL_ATTRIBUTE_CONTACT_US = "contactUs";
    public static final String VIEW_CONTACT_US            = "public/contact-us";

    @Autowired
    @Qualifier("smtpEmailService")
    private EmailService emailService;

    @GetMapping(UrlConstants.CONTACT_US)
    public ModelAndView contactUs() {
        ModelAndView mav = new ModelAndView(VIEW_CONTACT_US);
        mav.addObject(MODEL_ATTRIBUTE_CONTACT_US, new ContactUs());
        return mav;
    }

    @PostMapping(UrlConstants.CONTACT_US)
    public ModelAndView sendMessageContactUs(@ModelAttribute(MODEL_ATTRIBUTE_CONTACT_US) ContactUs contactUs) {
        ModelAndView mav = new ModelAndView(VIEW_CONTACT_US);
        emailService.sendFeedbackEmail(contactUs);
        return mav;
    }
}
