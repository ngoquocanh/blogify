package com.quopri.blogify.controller.stranger;

import com.quopri.blogify.components.WebUI;
import com.quopri.blogify.constants.UrlConstants;
import com.quopri.blogify.controller.BaseController;
import com.quopri.blogify.dto.ContactUsDTO;
import com.quopri.blogify.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class ContactUsController extends BaseController {

    public static final String MODEL_ATTRIBUTE_CONTACT_US = "contactUs";
    public static final String VIEW_CONTACT_US            = "public/contact-us";
    public static final String FEEDBACK_MESSAGE_KEY_CONTACT_US_SUCCESS = "feedback.message.contact-us.success";

    @Autowired
    private WebUI webUI;

    @Autowired
    @Qualifier("smtpEmailService")
    private EmailService emailService;

    @GetMapping(UrlConstants.CONTACT_US)
    public ModelAndView contactUs() {
        ModelAndView mav = new ModelAndView(VIEW_CONTACT_US);
        mav.addObject(MODEL_ATTRIBUTE_CONTACT_US, new ContactUsDTO());
        return mav;
    }

    @PostMapping(UrlConstants.CONTACT_US)
    public ModelAndView sendMessageContactUs(@Valid @ModelAttribute(MODEL_ATTRIBUTE_CONTACT_US) ContactUsDTO contactUs,
                                             BindingResult bindingResult, RedirectAttributes attributes) {
        ModelAndView mav = new ModelAndView();
        if (bindingResult.hasErrors()) {
            mav.addObject(MODEL_ATTRIBUTE_CONTACT_US, contactUs);
            mav.setViewName(VIEW_CONTACT_US);
        } else {
            emailService.sendFeedbackEmail(contactUs);
            webUI.addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_CONTACT_US_SUCCESS);
            mav.setViewName(redirectTo(UrlConstants.CONTACT_US));
        }
        return mav;
    }
}
