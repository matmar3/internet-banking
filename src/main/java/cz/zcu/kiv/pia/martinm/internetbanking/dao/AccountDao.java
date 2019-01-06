package cz.zcu.kiv.pia.martinm.internetbanking.dao;

import cz.zcu.kiv.pia.martinm.internetbanking.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Account data access object.
 *
 * Date: 26.12.2018
 *
 * @author Martin Matas
 */
@Repository
public interface AccountDao extends JpaRepository<Account, Integer> {

    /**
     * Finds an account by account number. If account with given account number
     * not exists then it returns null.
     *
     * @param accountNumber - account number of account
     * @return founded account or null
     */
    Account findByAccountNumber(String accountNumber);

}
