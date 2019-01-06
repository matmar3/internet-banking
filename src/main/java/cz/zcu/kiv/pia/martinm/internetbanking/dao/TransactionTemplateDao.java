package cz.zcu.kiv.pia.martinm.internetbanking.dao;

import cz.zcu.kiv.pia.martinm.internetbanking.domain.TransactionTemplate;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Transaction template data access object.
 *
 * Date: 30.12.2018
 *
 * @author Martin Matas
 */
@Repository
public interface TransactionTemplateDao extends JpaRepository<TransactionTemplate, Integer> {

    /**
     * Finds all templates by given owner. If no templates found then it returns empty list.
     *
     * @param owner - owner of templates
     * @return list of founded templates
     */
    List<TransactionTemplate> findAllByOwner(User owner);

    /**
     * Finds template by id and owner. If no template found then it returns null.
     *
     * @param owner - owner of template
     * @param id - identifier of template
     * @return founded template or null
     */
    TransactionTemplate findByOwnerAndId(User owner, Integer id);

}
