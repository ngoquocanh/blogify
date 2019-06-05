package com.quopri.blogify.repository;

import com.quopri.blogify.entity.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {

    /**
     * Returns a Account given a username or null if not found.
     * @param username The username
     * @return a Account given a username or null if not found.
     */
    Account findByUsername(String username);

    /**
     * Returns a Account given a email or null if not found.
     * @param email The email
     * @return a Account given a email or null if not found.
     */
    Account findByEmail(String email);

//    @Modifying
    @Query("update Account acc set acc.password = :password where acc.id = :id")
    void updatePassword(@Param("id") Long accountId, @Param("password") String password);
}
