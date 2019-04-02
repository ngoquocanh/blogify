package com.keybds.springblog.repository;

import com.keybds.springblog.model.Account;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {
    Account findByUsername(String username);
}
