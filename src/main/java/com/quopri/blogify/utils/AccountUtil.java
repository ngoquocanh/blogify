package com.quopri.blogify.utils;

import com.quopri.blogify.dto.AccountInfoDTO;
import com.quopri.blogify.model.Account;

public class AccountUtil {

    /**
     * Account Info DTO to Account
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
}
