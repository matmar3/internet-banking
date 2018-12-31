package cz.zcu.kiv.pia.martinm.internetbanking.dao;

import cz.zcu.kiv.pia.martinm.internetbanking.domain.TransactionTemplate;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Date: 30.12.2018
 *
 * @author Martin Matas
 */
@Repository
public interface TransactionTemplateDao extends JpaRepository<TransactionTemplate, Integer> {

    List<TransactionTemplate> findAllByOwner(User owner);

    TransactionTemplate findByOwnerAndId(User owner, Integer id);

}
