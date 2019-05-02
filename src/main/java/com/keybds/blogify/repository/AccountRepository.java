package com.keybds.blogify.repository;

import com.keybds.blogify.model.Account;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {
    Account findByUsername(String username);
}
