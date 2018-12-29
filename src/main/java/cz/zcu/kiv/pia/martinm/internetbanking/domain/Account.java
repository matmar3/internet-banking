package cz.zcu.kiv.pia.martinm.internetbanking.domain;

import cz.zcu.kiv.pia.martinm.internetbanking.service.CurrencyUtils;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Account entity.
 *
 * Date: 20.12.2018
 *
 * @author Martin Matas
 */
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "Account")
public class Account implements DataTransferObject<Integer> {

    public Account() {
    }

    public Account(String currency, String accountNumber, String cardNumber, User user) {
        this.currency = currency;
        this.accountNumber = accountNumber;
        this.cardNumber = cardNumber;
        this.balance = new BigDecimal(0);
        this.user = user;
    }

    /**
     * Account's identifier.
     */
    @Id
        @Column(nullable = false, name = "ID")
        @ToString.Include
        @EqualsAndHashCode.Include
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        private Integer id;

    /**
     * Account's currency.
     */
    @Column(nullable = false, name = "Currency")
    private String currency;

    /**
     * Account's number.
     */
    @Column(nullable = false, name = "AccountNumber")
        private String accountNumber;

    /**
     * Card number assigned to this account.
     */
    @Column(nullable = false, name = "CardNumber")
        private String cardNumber;

    /**
     * Account's actual balance.
     */
    @Column(nullable = false, name = "Balance")
        private BigDecimal balance;

    /**
     * Account's owner.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_ID")
        private User user;

    // Mappings

    @Override
    public Integer getId() {
        return id;
    }

    public String getCurrency() {
        return currency;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Transient
    public String getFormatedBalance() {
        return CurrencyUtils.format(balance, currency);
    }

    public User getUser() {
        return user;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
