package cz.zcu.kiv.pia.martinm.internetbanking.dao;

import cz.zcu.kiv.pia.martinm.internetbanking.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Date: 26.12.2018
 *
 * @author Martin Matas
 */
@Repository
public interface AccountDao extends JpaRepository<Account, Integer> {

    Account findByAccountNumber(String accountNumber);

}
