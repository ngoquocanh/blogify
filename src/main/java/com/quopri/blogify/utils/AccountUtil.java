package com.quopri.blogify.utils;

import com.quopri.blogify.constants.UrlConstants;
import com.quopri.blogify.dto.AccountInfoDTO;
import com.quopri.blogify.dto.AuthenticityTokenDTO;
import com.quopri.blogify.entity.Account;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;

public class AccountUtil {

    /**
     * Account Info DTO to Account
     *
     * @param accountInfoDTO
     * @return
     */
    public static Account convertToEntity(AccountInfoDTO accountInfoDTO) {
        Account account = new Account();
        account.setId(accountInfoDTO.getId());
        account.setFirstName(accountInfoDTO.getFirstName());
        account.setLastName(accountInfoDTO.getLastName());
        account.setUsername(accountInfoDTO.getUsername());
        account.setEmail(accountInfoDTO.getEmail());
        return account;
    }

    /**
     * Builds and returns the URL to reset the account password.
     * @param request The Http Servlet Request
     * @param accountId The account id
     * @param token The token
     * @return The URL to reset the account password.
     */
    public static String createPasswordResetUrl(HttpServletRequest request, Long accountId, String token) {
        String passwordResetUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath() + UrlConstants.ACCOUNT_CHANGE_PASSWORD + "?id=" + accountId + "&token=" + token;
        return passwordResetUrl;
    }

    /**
     * Builds and returns the URL to reset the account password.
     * @param request The Http Servlet Request
     * @param email The account email
     * @param token The token
     * @return The URL to reset the account password.
     */
    public static String createPasswordResetUrl(HttpServletRequest request, String email, String token) {
        String passwordResetUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath() + UrlConstants.ACCOUNT_CHANGE_PASSWORD + "?email=" + email + "&token=" + token;
        return passwordResetUrl;
    }

    /**
     * Extract token from url
     * @param token     The token submit by end user
     * @param password  The secret key password
     * @param salt      The secret key salt
     * @return
     * @throws Exception
     */
    public static AuthenticityTokenDTO extractResetToken(String token, String password, String salt) throws Exception {
        token = URLDecoder.decode(token, CipherHelper.UTF8);
        String decrypt = CipherHelper.decrypt(token, password, salt);
        AuthenticityTokenDTO authenticityToken = JacksonJsonUtil.fromJson(decrypt, AuthenticityTokenDTO.class);
        return authenticityToken;
    }
}
