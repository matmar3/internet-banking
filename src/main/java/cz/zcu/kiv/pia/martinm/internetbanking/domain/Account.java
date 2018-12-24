package cz.zcu.kiv.pia.martinm.internetbanking.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

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

    /**
     * Account's identifier.
     */
    @Id
        @Column(nullable = false, name = "AccountNumber")
        @ToString.Include
        @EqualsAndHashCode.Include
        private Integer accountNumber;

    /**
     * Card number assigned to this account.
     */
    @Column(nullable = false, name = "CardNumber")
        private String cardNumber;

    /**
     * Account's actual balance.
     */
    @Column(nullable = false, name = "Balance")
        private Integer balance = 0;

    /**
     * Account's owner.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_ID")
        private User user;

    // Mappings

    @Override
    public Integer getId() {
        return accountNumber;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public Integer getBalance() {
        return balance;
    }

    public User getUser() {
        return user;
    }
}
