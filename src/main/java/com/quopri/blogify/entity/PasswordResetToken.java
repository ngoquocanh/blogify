package com.quopri.blogify.entity;

import com.quopri.blogify.converters.LocalDateTimeConverter;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "password_reset_token")
public class PasswordResetToken implements Serializable {

    private static final Integer DEFAULT_TOKEN_IN_MINUTES = 120;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "token")
    private String token;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "expiry_date")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime expiryDate;

    public PasswordResetToken() {

    }

    /**
     * Full constructor
     * @param token The account token. It must not be null.
     * @param account The account for which the token should be created. It must not be null.
     * @param creationDateTime The date time when this request was created. It must not be null.
     * @param expirationInMinutes The length, in minutes, for which this token will be valid. if zero, it will be
     *                            assigned a default value of 120 (2 hours)
     * @throws IllegalArgumentException If the token, account or creation date time are null
     */
    public PasswordResetToken(String token, Account account, LocalDateTime creationDateTime, int expirationInMinutes) {
        if (token == null || account == null || creationDateTime == null) {
            throw new IllegalArgumentException("token, account and creation date time can't be null");
        }
        if (expirationInMinutes == 0) {
            expirationInMinutes = DEFAULT_TOKEN_IN_MINUTES;
        }
        this.token = token;
        this.account = account;
        this.expiryDate = creationDateTime.plusMinutes(expirationInMinutes);
    }
}
