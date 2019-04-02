package com.keybds.springblog.controller;

import com.keybds.springblog.containers.PageHolder;
import com.keybds.springblog.containers.PageUtil;
import com.keybds.springblog.enums.RoleEnum;
import com.keybds.springblog.model.AccountDetails;
import com.keybds.springblog.model.Authority;
import com.keybds.springblog.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;
import java.util.List;

import static java.lang.Math.toIntExact;

public abstract class BaseController {

    protected static final String MODEL_ATTRIBUTE_PAGER                 = "pager";


    /**
     * For pagination
     */
    protected static final List<Integer> LIST_PAGE_SIZE_OPTIONS = Arrays.asList(5, 10, 20, 50);
    protected static final Integer PAGE_RANGE = 5;
    protected static final Integer PAGE_SIZE = 10;
    protected static final Integer PAGE_INDEX = 1;

    @Autowired
    @Qualifier(value = "accountService")
    private AccountService accountService;

    @Autowired
    private AuthenticationTrustResolver authenticationTrustResolver;

    @ModelAttribute("accountDetails")
    public AccountDetails retrieveCurrentAccountLoggedIn() {
        return getLoggedInInfo();
    }

    /**
     * Example code using sec tag:
     * <li sec:authorize="hasRole('ROLE_ADMIN')" class="nav-item">
     *     <a class="nav-link" href="#">Manage</a>
     * </li>
     *
     * -----------------------------------------------------
     *
     * Or using checkAdmin method:
     * <li th:if="${isAdmin == true}" class="nav-item">
     *     <a class="nav-link" href="#">Manage</a>
     * </li>
     *
     * @return
     */
    @ModelAttribute("isAdmin")
    public boolean checkAdmin() {
        return isAdmin(getLoggedInInfo());
    }

    @ModelAttribute("isUser")
    public boolean checkUser() {
        return isUser(getLoggedInInfo());
    }

    /**
     * Check account is already logged-in or not
     * @return
     */
    protected boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !authenticationTrustResolver.isAnonymous(authentication);
    }

    /**
     * get current account is already logged-in or null otherwise
     * @return
     */
    protected AccountDetails getLoggedInInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                AccountDetails accountDetails = (AccountDetails) accountService.loadUserByUsername(userDetails.getUsername());
                return accountDetails;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * check if current account is Admin
     * @param accountDetails
     * @return
     */
    protected boolean isAdmin(AccountDetails accountDetails) {
        if (accountDetails != null) {
            if (accountDetails.getAccount() != null) {
                return (accountDetails.getAccount().getAuthorities().contains(new Authority(RoleEnum.ADMIN))) ? true : false;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * check if current account is User
     * @param accountDetails
     * @return
     */
    protected boolean isUser(AccountDetails accountDetails) {
        if (accountDetails != null) {
            if (accountDetails.getAccount() != null) {
                return (accountDetails.getAccount().getAuthorities().contains(new Authority(RoleEnum.USER))) ? true : false;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    protected boolean isSortDir(String sortDir) {
        return (sortDir.equalsIgnoreCase("desc") || sortDir.equalsIgnoreCase("asc")) ? true : false;
    }

    /**
     * Range value from 1 to MAX_VALUE of Integer
     * @param text
     * @param defaultValue
     * @return
     */
    protected Integer parseInt(String text, Integer defaultValue) {
        try {
            return text.matches("^[1-9]{1}[0-9]*$") ? Integer.parseInt(text) : defaultValue;
        } catch (NumberFormatException numberEx) {
            return Integer.MAX_VALUE;
        }
    }

    /**
     * Range value from 1 to MAX_VALUE of Long
     * @param text
     * @param defaultValue
     * @return
     */
    protected Long parseLong(String text, Long defaultValue) {
        try {
            return text.matches("^[1-9]{1}[0-9]*$") ? Long.parseLong(text) : defaultValue;
        } catch (NumberFormatException numberEx) {
            return Long.MAX_VALUE;
        }
    }

    /**
     * Check pageSize as param is in list page size predefine
     *
     * @param pageSize
     * @return Boolean
     */
    protected boolean isPageSize(Integer pageSize) {
        return LIST_PAGE_SIZE_OPTIONS.contains(pageSize);
    }

    protected String redirectTo(String url) {
        return "redirect:" + url;
    }

    protected static <T> PageUtil<T> manualBuildPager(List<T> listOfRecords, Integer pageIndex, Integer pageSize, Long totalRecords) {
        PageUtil<T> pager = new PageUtil<>(listOfRecords, pageIndex, pageSize, LIST_PAGE_SIZE_OPTIONS, PAGE_RANGE, toIntExact(totalRecords));
        return pager;
    }

    protected static <T> PageHolder<T> manualBuildPager(PageUtil<T> pageUtil, String baseUrl) {
        PageHolder<T> pageHolder = new PageHolder<>(pageUtil, baseUrl);
        return pageHolder;
    }
}
