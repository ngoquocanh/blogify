package com.keybds.springblog.utils;

import com.keybds.springblog.dto.AccountInfoDTO;
import com.keybds.springblog.model.Account;

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
