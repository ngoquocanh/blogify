package com.quopri.blogify.repository;

import com.quopri.blogify.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String token);

    @Query("select prt from PasswordResetToken prt inner join prt.account acc where prt.account.id = ?1")
    Set<PasswordResetToken> findAllByAccountId(Long accountId);
}
