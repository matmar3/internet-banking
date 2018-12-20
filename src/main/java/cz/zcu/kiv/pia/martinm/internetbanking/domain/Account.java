package cz.zcu.kiv.pia.martinm.internetbanking.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/**
 * Date: 20.12.2018
 *
 * @author Martin Matas
 */
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "Account")
public class Account implements DataTransferObject<Integer> {

    @Id
        @Column(nullable = false, name = "AccountNumber")
        @ToString.Include
        @EqualsAndHashCode.Include
        private Integer accountNumber;

    @Column(nullable = false, name = "CardNumber")
        private String cardNumber;

    @Column(nullable = false, name = "Balance")
        private Integer balance = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_ID")
        private User user;

    @Override
    public Integer getId() {
        return accountNumber;
    }
}
