package com.keybds.springblog.controller.admin;

import com.keybds.springblog.components.WebUI;
import com.keybds.springblog.constants.UrlConstants;
import com.keybds.springblog.containers.PageHolder;
import com.keybds.springblog.containers.PageUtil;
import com.keybds.springblog.controller.BaseController;
import com.keybds.springblog.dto.AccountDTO;
import com.keybds.springblog.dto.AccountEmailDTO;
import com.keybds.springblog.dto.AccountInfoDTO;
import com.keybds.springblog.enums.StatusMessageCode;
import com.keybds.springblog.exceptions.MvcException;
import com.keybds.springblog.model.Account;
import com.keybds.springblog.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AdminAccountController extends BaseController {

    @Autowired
    private WebUI webUI;

    @Autowired
    private AccountService accountService;

    protected static final String VIEW_ADMIN_ACCOUNTS_LIST  = "admin/accounts/list";
    protected static final String VIEW_ADMIN_ACCOUNT_ADD    = "admin/accounts/add";
    protected static final String VIEW_ADMIN_ACCOUNT_UPDATE = "admin/accounts/update";

    public static final String MODEL_ATTRIBUTE_ACCOUNTS          = "accounts";
    public static final String MODEL_ATTRIBUTE_ACCOUNT           = "account";
    public static final String MODEL_ATTRIBUTE_ACCOUNT_INFO      = "accountInfo";
    public static final String MODEL_ATTRIBUTE_ACCOUNT_USERNAME  = "accountUsername";
    public static final String MODEL_ATTRIBUTE_ACCOUNT_EMAIL     = "accountEmail";
    public static final String MODEL_ATTRIBUTE_ACCOUNT_SECURITY  = "accountSecurity";
    public static final String MODEL_ATTRIBUTE_ACCOUNT_ID        = "id";

    public static final String FEEDBACK_MESSAGE_KEY_ACCOUNT_ADDED   = "feedback.message.account.added";
    public static final String FEEDBACK_MESSAGE_KEY_ACCOUNT_UPDATED = "feedback.message.account.updated";

    public static final String TAB_MODEL_ACTIVE                 = "active";
    public static final String TAB_MODEL_ACTIVE_VALUE_INFO      = "k-account-general-tab";
    public static final String TAB_MODEL_ACTIVE_VALUE_EMAIL     = "k-account-email-tab";
    public static final String TAB_MODEL_ACTIVE_VALUE_USERNAME  = "k-account-username-tab";
    public static final String TAB_MODEL_ACTIVE_VALUE_SECURITY  = "k-account-security-tab";

    @ModelAttribute(AdminAccountController.TAB_MODEL_ACTIVE)
    public String setActiveTab() {
        return TAB_MODEL_ACTIVE_VALUE_INFO;
    }

//    @Autowired
//    @Qualifier("accountValidator")
//    private Validator validator;
//
//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        binder.setValidator(validator);
//    }

    @GetMapping(UrlConstants.ADMIN_ACCOUNTS_LIST)
    public ModelAndView retrieveAllAccountsPageable(
            @RequestParam(value = AdminPostController.MODEL_ATTRIBUTE_PAGE_INDEX,
                    defaultValue = AdminPostController.MODEL_ATTRIBUTE_PAGE_DEFAULT) String strPageIndex) throws MvcException {
        ModelAndView mav = new ModelAndView(VIEW_ADMIN_ACCOUNTS_LIST);
        Integer pageIndex = parseInt(strPageIndex, 0);
        if (pageIndex != 0) {
            Page<Account> accounts = accountService.getAllAccounts(PageRequest.of(pageIndex - 1, PAGE_SIZE));
            if (accounts.hasContent()) {
                PageUtil<Account> pager = manualBuildPager(accounts.getContent(), accounts.getNumber(), accounts.getSize(), accounts.getTotalElements());
                PageHolder<Account> pageAccountsHolder = new PageHolder(pager, UrlConstants.ADMIN_ACCOUNTS_LIST_BASE_URL);
                mav.addObject(MODEL_ATTRIBUTE_PAGER, pageAccountsHolder);
            } else {
                mav.addObject(MODEL_ATTRIBUTE_PAGER, null);
            }
        } else {
            mav.setViewName(redirectTo(UrlConstants.ADMIN_ACCOUNTS_LIST_BASE_URL.concat(PAGE_INDEX.toString())));
        }
        return mav;
    }

    @GetMapping(UrlConstants.ADMIN_ACCOUNT_ADD)
    public ModelAndView openAccountFormAdd() {
        ModelAndView mav = new ModelAndView(VIEW_ADMIN_ACCOUNT_ADD);
        AccountDTO accountDTO = new AccountDTO();
        mav.addObject(MODEL_ATTRIBUTE_ACCOUNT, accountDTO);
        return mav;
    }

    @PostMapping(UrlConstants.ADMIN_ACCOUNT_ADD)
    public ModelAndView createAccount(@Validated @ModelAttribute(MODEL_ATTRIBUTE_ACCOUNT) AccountDTO accountDTO,
                                BindingResult bindingResult, SessionStatus sessionStatus,
                                      RedirectAttributes attributes) throws MvcException {
        try {
            ModelAndView mav = new ModelAndView();
            if (bindingResult.hasErrors()) {
                mav.setViewName(VIEW_ADMIN_ACCOUNT_ADD);
                return mav;
            }
            Account account = accountService.createAccount(accountDTO);
            sessionStatus.setComplete();
            webUI.addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_ACCOUNT_ADDED, account.getEmail());
            mav.setViewName(redirectTo(UrlConstants.ADMIN_ACCOUNTS_LIST));
            return mav;
        } catch (Exception e) {
            throw new MvcException(StatusMessageCode.FAIL);
        }
    }

    @GetMapping(UrlConstants.ADMIN_ACCOUNT_UPDATE)
    public ModelAndView openFormUpdateAccount(@PathVariable(MODEL_ATTRIBUTE_ACCOUNT_ID) Long id) throws MvcException {
        ModelAndView mav = new ModelAndView(VIEW_ADMIN_ACCOUNT_UPDATE);
        Optional<Account> accountFound = accountService.retrieveAccountById(id);
        if (accountFound.isPresent()) {
            // first name, last name
            AccountInfoDTO accountInfo = new AccountInfoDTO();
            accountInfo.setId(accountFound.get().getId());
            accountInfo.setFirstName(accountFound.get().getFirstName());
            accountInfo.setLastName(accountFound.get().getLastName());
            mav.addObject(MODEL_ATTRIBUTE_ACCOUNT_INFO, accountInfo);

            // email
            AccountEmailDTO accountEmail = new AccountEmailDTO();
            accountEmail.setId(accountFound.get().getId());
            accountEmail.setEmail(accountFound.get().getEmail());
            mav.addObject(MODEL_ATTRIBUTE_ACCOUNT_EMAIL, accountEmail);
        } else {
            throw new MvcException(StatusMessageCode.ACCOUNT_NOT_FOUND);
        }
        return mav;
    }

    /**
     * just update firstName, lastName
     * @param accountInfo
     * @param bindingResult
     * @param attributes
     * @return
     */
    @PostMapping(UrlConstants.ADMIN_ACCOUNT_UPDATE_INFO)
    public ModelAndView updateAccountInfo(@Valid @ModelAttribute(MODEL_ATTRIBUTE_ACCOUNT_INFO) AccountInfoDTO accountInfo,
                                      BindingResult bindingResult, RedirectAttributes attributes) {
        ModelAndView mav = new ModelAndView();
        if (bindingResult.hasErrors()) {
            mav.addObject(TAB_MODEL_ACTIVE, TAB_MODEL_ACTIVE_VALUE_INFO);
            mav.addObject(MODEL_ATTRIBUTE_ACCOUNT_INFO, accountInfo);
            mav.setViewName(VIEW_ADMIN_ACCOUNT_UPDATE);
        } else {
            Account accountToUpdate = new Account();
            accountToUpdate.setId(accountInfo.getId());
            accountToUpdate.setFirstName(accountInfo.getFirstName());
            accountToUpdate.setLastName(accountInfo.getLastName());
            Account account = accountService.updateAccountInfo(accountToUpdate);
            webUI.addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_ACCOUNT_UPDATED, account.getEmail());
            mav.setViewName(redirectTo(UrlConstants.ADMIN_ACCOUNTS_LIST_BASE_URL.concat(PAGE_INDEX.toString())));
        }
        return mav;
    }

    /**
     * update email
     * @param accountEmail
     * @param bindingResult
     * @param attributes
     * @return
     */
    @PostMapping(UrlConstants.ADMIN_ACCOUNT_UPDATE_EMAIL)
    public ModelAndView updateAccountEmail(@Valid @ModelAttribute(MODEL_ATTRIBUTE_ACCOUNT_EMAIL) AccountEmailDTO accountEmail,
                                           BindingResult bindingResult, RedirectAttributes attributes) {
        ModelAndView mav = new ModelAndView();
        if (bindingResult.hasErrors()) {
            mav.addObject(TAB_MODEL_ACTIVE, TAB_MODEL_ACTIVE_VALUE_EMAIL);
            mav.addObject(MODEL_ATTRIBUTE_ACCOUNT_EMAIL, accountEmail);
            mav.setViewName(VIEW_ADMIN_ACCOUNT_UPDATE);
        } else {
            Account accountToUpdate = new Account();
            accountToUpdate.setId(accountEmail.getId());
            accountToUpdate.setEmail(accountEmail.getEmail());
            Account account = accountService.updateAccountEmail(accountToUpdate);
            webUI.addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_ACCOUNT_UPDATED, account.getEmail());
            mav.setViewName(redirectTo(UrlConstants.ADMIN_ACCOUNTS_LIST_BASE_URL.concat(PAGE_INDEX.toString())));
        }
        return mav;
    }

    /**
     * update username
     * @param accountDTO
     * @param bindingResult
     * @param sessionStatus
     * @param attributes
     * @return
     */
    @PostMapping(UrlConstants.ADMIN_ACCOUNT_UPDATE_USERNAME)
    public ModelAndView updateAccountUsername(@Valid @ModelAttribute(MODEL_ATTRIBUTE_ACCOUNT) AccountDTO accountDTO,
                                      BindingResult bindingResult, SessionStatus sessionStatus, RedirectAttributes attributes) {
        ModelAndView mav = new ModelAndView();
        if (bindingResult.hasErrors()) {
            mav.addObject(TAB_MODEL_ACTIVE, TAB_MODEL_ACTIVE_VALUE_USERNAME);
            mav.addObject(MODEL_ATTRIBUTE_ACCOUNT, accountDTO);
            mav.setViewName(VIEW_ADMIN_ACCOUNT_UPDATE);
        } else {
            Account account = accountService.updateAccountUsername(accountDTO);
            sessionStatus.setComplete();
            webUI.addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_ACCOUNT_UPDATED, account.getEmail());
            mav.setViewName(redirectTo(UrlConstants.ADMIN_ACCOUNTS_LIST));
        }
        return mav;
    }

    @PostMapping(UrlConstants.ADMIN_ACCOUNT_UPDATE_SECURITY)
    public ModelAndView updateAccountSecurity(@Valid @ModelAttribute(MODEL_ATTRIBUTE_ACCOUNT) AccountDTO accountDTO,
                                           BindingResult bindingResult, SessionStatus sessionStatus, RedirectAttributes attributes) {
        ModelAndView mav = new ModelAndView();
        if (bindingResult.hasErrors()) {
            mav.addObject(TAB_MODEL_ACTIVE, TAB_MODEL_ACTIVE_VALUE_SECURITY);
            mav.addObject(MODEL_ATTRIBUTE_ACCOUNT, accountDTO);
            mav.setViewName(VIEW_ADMIN_ACCOUNT_UPDATE);
        } else {
            Account account = accountService.updateAccountSecurity(accountDTO);
            sessionStatus.setComplete();
            webUI.addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_ACCOUNT_UPDATED, account.getEmail());
            mav.setViewName(redirectTo(UrlConstants.ADMIN_ACCOUNTS_LIST));
        }
        return mav;
    }

    private AccountDTO convertToDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setFirstName(account.getFirstName());
        accountDTO.setLastName(account.getLastName());
        accountDTO.setEmail(account.getEmail());
        accountDTO.setUsername(account.getUsername());
        accountDTO.setPassword(account.getPassword());
        accountDTO.setNewPassword(account.getPassword());
        accountDTO.setConfirmPassword(account.getPassword());
        accountDTO.setEnabled(account.getEnabled());
        return accountDTO;
    }
}
