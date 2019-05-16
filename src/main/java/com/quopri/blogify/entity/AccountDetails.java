package com.quopri.blogify.entity;

public class AccountDetails extends org.springframework.security.core.userdetails.User {

    private Account account;

    public AccountDetails(Account account) {
        super(account.getUsername(), account.getPassword(), account.getAuthorities());
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public Long getId() {
        return account.getId();
    }

    public String getFullName() {
        return account.getFirstName() + " " + account.getLastName();
    }

    public String getIconAvatar() {
        String iconAvatar = "/images/avatar.png";
        return iconAvatar;
    }
}
