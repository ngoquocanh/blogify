package com.quopri.blogify.repository;

import com.quopri.blogify.model.Account;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {
    Account findByUsername(String username);
}