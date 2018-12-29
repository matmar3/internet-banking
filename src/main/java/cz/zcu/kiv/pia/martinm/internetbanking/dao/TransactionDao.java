package cz.zcu.kiv.pia.martinm.internetbanking.dao;

import cz.zcu.kiv.pia.martinm.internetbanking.domain.Account;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Date: 28.12.2018
 *
 * @author Martin Matas
 */
public interface TransactionDao extends JpaRepository<Transaction,Integer> {

    @Query("SELECT '*' FROM Transaction t WHERE (t.sourceAccount = :account OR t.targetAccount = :account) AND t.dueDate <= CURRENT_DATE ORDER BY t.dueDate DESC")
    List<Transaction> findAllByAccount(@Param("account") Account account);

}
