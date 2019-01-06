package cz.zcu.kiv.pia.martinm.internetbanking.dao;

import cz.zcu.kiv.pia.martinm.internetbanking.domain.Account;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Transaction data access object.
 *
 * Date: 28.12.2018
 *
 * @author Martin Matas
 */
@Repository
public interface TransactionDao extends JpaRepository<Transaction, Integer> {

    /**
     * Finds all transactions with given account as sender or receiver. Result set is influenced by
     * pageable attribute which defines size of result set and offset.
     *
     * @param account - sender or receiver of transactions
     * @param pageable - defines size of result set and offset where results set starts
     * @return one page of transactions
     */
    @Query("SELECT t FROM Transaction t WHERE t.sender = :account OR t.receiver = :account ORDER BY t.dueDate DESC")
    Page<Transaction> findAllByAccount(@Param("account") Account account, Pageable pageable);

}
