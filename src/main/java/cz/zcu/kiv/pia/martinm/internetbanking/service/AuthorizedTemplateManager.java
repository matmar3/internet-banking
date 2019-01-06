package cz.zcu.kiv.pia.martinm.internetbanking.service;

import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.TransactionTemplateDto;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.TransactionTemplate;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;

import java.util.List;

/**
 * Provides authorized operations with templates of transaction.
 *
 * Date: 03.01.2019
 *
 * @author Martin Matas
 */
public interface AuthorizedTemplateManager {

    /**
     * Creates new transaction template from given template values to given owner.
     *
     * @param newTemplate - template values
     * @param user - transaction template owner
     */
    void createTemplate(TransactionTemplateDto newTemplate, User user);

    /**
     * Finds all transaction templates for given owner.
     *
     * @param user - owner of templates
     * @return list of founded templates
     */
    List<TransactionTemplate> findAllTemplatesByUser(User user);

    /**
     * Finds specific transaction template by owner and template identifier.
     *
     * @param user - owner of the template
     * @param id - template's identifier
     * @return founded transaction template or null
     */
    TransactionTemplate findTemplateById(User user, Integer id);

    /**
     * Removes transaction template based on given identifier of the template.
     *
     * @param id - template's identifier
     */
    void removeTemplate(Integer id);

    /**
     * Edits transaction template with given template values.
     *
     * @param modifyTemplate - template values
     */
    void editTemplate(TransactionTemplateDto modifyTemplate);

}
