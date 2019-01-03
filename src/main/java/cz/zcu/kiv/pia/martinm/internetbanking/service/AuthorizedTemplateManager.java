package cz.zcu.kiv.pia.martinm.internetbanking.service;

import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.TransactionTemplateDto;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.TransactionTemplate;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;

import java.util.List;

/**
 * Date: 03.01.2019
 *
 * @author Martin Matas
 */
public interface AuthorizedTemplateManager {

    void createTemplate(TransactionTemplateDto newTemplate, User user);

    List<TransactionTemplate> findAllTemplatesByUser(User user);

    TransactionTemplate findTemplateById(User user, Integer id);

    void removeTemplate(Integer id);

    void editTemplate(TransactionTemplateDto modifyTemplate);

}
