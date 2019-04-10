package com.keybds.springblog.controller.admin;

import com.keybds.springblog.components.WebUI;
import com.keybds.springblog.constants.UrlConstants;
import com.keybds.springblog.containers.PageHolder;
import com.keybds.springblog.containers.PageUtil;
import com.keybds.springblog.controller.BaseController;
import com.keybds.springblog.dto.*;
import com.keybds.springblog.enums.StatusMessageCode;
import com.keybds.springblog.exceptions.MvcException;
import com.keybds.springblog.model.Account;
import com.keybds.springblog.service.AccountService;
import com.keybds.springblog.utils.AccountUtil;
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
import java.util.List;
import java.util.Optional;

@Controller
public class AdminAccountController extends BaseController {

    @Autowired
    private WebUI webUI;

    @Autowired
    private AccountService accountService;

    protected static final String VIEW_ADMIN_ACCOUNTS_LIST          = "admin/accounts/list";
    protected static final String VIEW_ADMIN_ACCOUNT_ADD            = "admin/accounts/add";
    protected static final String VIEW_ADMIN_ACCOUNT_UPDATE         = "admin/accounts/update";
    protected static final String VIEW_ADMIN_ACCOUNT_RESET_PASSWORD = "admin/accounts/reset-password";

    public static final String MODEL_ATTRIBUTE_ACCOUNTS          = "accounts";
    public static final String MODEL_ATTRIBUTE_ACCOUNT           = "account";
    public static final String MODEL_ATTRIBUTE_ACCOUNT_INFO      = "accountInfo";
    public static final String MODEL_ATTRIBUTE_ACCOUNT_SECURITY  = "accountSecurity";
    public static final String MODEL_ATTRIBUTE_ACCOUNT_ID        = "id";

    public static final String FEEDBACK_MESSAGE_KEY_ACCOUNT_ADDED    = "feedback.message.account.added";
    public static final String FEEDBACK_MESSAGE_KEY_ACCOUNT_UPDATED  = "feedback.message.account.updated";
    public static final String FEEDBACK_MESSAGE_KEY_ACCOUNTS_DELETED = "feedback.message.accounts.deleted";

//    @Autowired
//    @Qualifier("accountValidator")
//    private Validator validator;
//
//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        binder.setValidator(validator);
//    }

    /**
     * URL: /admin/accounts?p=
     * METHOD: GET
     * @param strPageIndex
     * @return pager
     * @throws MvcException
     */
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
    public ModelAndView openFormUpdateAccountInfo(@PathVariable(MODEL_ATTRIBUTE_ACCOUNT_ID) Long id) throws MvcException {
        ModelAndView mav = new ModelAndView(VIEW_ADMIN_ACCOUNT_UPDATE);
        Optional<Account> accountFound = accountService.retrieveAccountById(id);
        if (accountFound.isPresent()) {
            AccountInfoDTO accountInfo = new AccountInfoDTO();
            accountInfo.setId(accountFound.get().getId());
            accountInfo.setFirstName(accountFound.get().getFirstName());
            accountInfo.setLastName(accountFound.get().getLastName());
            accountInfo.setUsername(accountFound.get().getUsername());
            accountInfo.setEmail(accountFound.get().getEmail());
            mav.addObject(MODEL_ATTRIBUTE_ACCOUNT_INFO, accountInfo);
        } else {
            throw new MvcException(StatusMessageCode.ACCOUNT_NOT_FOUND);
        }
        return mav;
    }

    /**
     * just update firstName, lastName, username, email
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
            mav.addObject(MODEL_ATTRIBUTE_ACCOUNT_INFO, accountInfo);
            mav.setViewName(VIEW_ADMIN_ACCOUNT_UPDATE);
        } else {
            Account accountToUpdate = AccountUtil.convertToEntity(accountInfo);
            Account account = accountService.updateAccountInfo(accountToUpdate);
            webUI.addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_ACCOUNT_UPDATED, account.getEmail());
            mav.setViewName(redirectTo(UrlConstants.ADMIN_ACCOUNTS_LIST_BASE_URL.concat(PAGE_INDEX.toString())));
        }
        return mav;
    }

    @GetMapping(UrlConstants.ADMIN_ACCOUNT_RESET_PASSWORD_ID)
    public ModelAndView openFormUpdateAccountSecurity(@PathVariable(MODEL_ATTRIBUTE_ACCOUNT_ID) Long id) throws MvcException {
        ModelAndView mav = new ModelAndView(VIEW_ADMIN_ACCOUNT_RESET_PASSWORD);
        Optional<Account> accountFound = accountService.retrieveAccountById(id);
        if (accountFound.isPresent()) {
            AccountSecurityDTO accountSecurity = new AccountSecurityDTO();
            accountSecurity.setId(accountFound.get().getId());
            mav.addObject(MODEL_ATTRIBUTE_ACCOUNT_SECURITY, accountSecurity);
        } else {
            throw new MvcException(StatusMessageCode.ACCOUNT_NOT_FOUND);
        }
        return mav;
    }

    /**
     * Need to update, check new password and confirm password is same
     * @param accountSecurity
     * @param bindingResult
     * @param attributes
     * @return
     */
    @PostMapping(UrlConstants.ADMIN_ACCOUNT_RESET_PASSWORD)
    public ModelAndView updateAccountSecurity(@Valid @ModelAttribute(MODEL_ATTRIBUTE_ACCOUNT_SECURITY) AccountSecurityDTO accountSecurity,
                                           BindingResult bindingResult, RedirectAttributes attributes) {
        ModelAndView mav = new ModelAndView();
        if (bindingResult.hasErrors()) {
            mav.addObject(MODEL_ATTRIBUTE_ACCOUNT_SECURITY, accountSecurity);
            mav.setViewName(VIEW_ADMIN_ACCOUNT_RESET_PASSWORD);
        } else {
            Account accountToUpdate = new Account();
            accountToUpdate.setPassword(accountSecurity.getNewPassword());
            accountToUpdate.setId(accountSecurity.getId());
            Account account = accountService.updateAccountSecurity(accountToUpdate);
            webUI.addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_ACCOUNT_UPDATED, account.getEmail());
            mav.setViewName(redirectTo(UrlConstants.ADMIN_ACCOUNTS_LIST_BASE_URL.concat(PAGE_INDEX.toString())));
        }
        return mav;
    }

    /**
     * URL: /admin/accounts/delete
     * METHOD: POST
     * @param strAccountIds
     * @param attributes
     * @return
     * @throws MvcException
     */
    @PostMapping(UrlConstants.ADMIN_ACCOUNTS_DELETE)
    public ModelAndView deleteAccounts(@RequestParam(MODEL_ATTRIBUTE_ACCOUNT_ID) String[] strAccountIds, RedirectAttributes attributes) throws MvcException {
        ModelAndView mav = new ModelAndView();
        List<Long> accountIds = parseLongIds(strAccountIds);
        accountService.deleteAccounts(accountIds);
        webUI.addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_ACCOUNTS_DELETED);
        mav.setViewName(redirectTo(UrlConstants.ADMIN_ACCOUNTS_LIST_BASE_URL.concat(PAGE_INDEX.toString())));
        return mav;
    }
}
