package cz.zcu.kiv.pia.martinm.internetbanking.service;

import cz.zcu.kiv.pia.martinm.internetbanking.EntityNotFoundException;
import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.TransactionTemplateDto;
import cz.zcu.kiv.pia.martinm.internetbanking.dao.TransactionTemplateDao;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.TransactionTemplate;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of TransactionTemplateManager, for more details see {@link TransactionTemplateManager}.
 *
 * Date: 03.01.2019
 *
 * @author Martin Matas
 */
@Service
public class DefaultTemplateManager implements  TransactionTemplateManager {

    private TransactionTemplateDao transactionTemplateDao;

    private MessageProvider messageProvider;

    public DefaultTemplateManager(
            TransactionTemplateDao transactionTemplateDao,
            MessageProvider messageProvider) {
        this.transactionTemplateDao = transactionTemplateDao;
        this.messageProvider = messageProvider;
    }

    @Override
    public AuthorizedTemplateManager authorize(User user) {
        return new DefaultAuthorizedTemplateManager(user);
    }

    /**
     * Implementation of AuthorizedTemplateManager, for more details see {@link AuthorizedTemplateManager}.
     */
    private class DefaultAuthorizedTemplateManager implements AuthorizedTemplateManager {

        private User currentUser;

        DefaultAuthorizedTemplateManager(User currentUser) {
            this.currentUser = currentUser;
        }

        @Override
        public void createTemplate(TransactionTemplateDto newTemplate, User user) {
            if (!currentUser.getRole().equals(User.Role.ADMIN.name()) && !currentUser.getId().equals(user.getId())) {
                throw new AccessDeniedException(messageProvider.getMessage("error.accessDenied.modifyOtherUserData"));
            }

            TransactionTemplate template = new TransactionTemplate(
                    newTemplate.getTemplateName(),
                    newTemplate.getReceiverAccountNumber(),
                    newTemplate.getSentAmount(),
                    newTemplate.getSenderAccountNumber(),
                    newTemplate.getDueDate(),
                    newTemplate.getConstantSymbol(),
                    newTemplate.getVariableSymbol(),
                    newTemplate.getSpecificSymbol(),
                    newTemplate.getMessage(),
                    user
            );

            transactionTemplateDao.save(template);
        }

        @Override
        public List<TransactionTemplate> findAllTemplatesByUser(User user) {
            if (!currentUser.getRole().equals(User.Role.ADMIN.name()) && !currentUser.getId().equals(user.getId())) {
                throw new AccessDeniedException(messageProvider.getMessage("error.accessDenied.showOtherUserData"));
            }

            return transactionTemplateDao.findAllByOwner(user);
        }

        @Override
        public TransactionTemplate findTemplateById(User user, Integer id) {
            if (!currentUser.getRole().equals(User.Role.ADMIN.name()) && !currentUser.getId().equals(user.getId())) {
                throw new AccessDeniedException(messageProvider.getMessage("error.accessDenied.showOtherUserData"));
            }

            TransactionTemplate template = transactionTemplateDao.findByOwnerAndId(user, id);
            if (template == null) {
                throw new EntityNotFoundException(messageProvider.getMessage("error.entityNotFound", TransactionTemplate.class.getSimpleName()));
            }

            return template;
        }

        @Override
        public void removeTemplate(Integer id) {
            TransactionTemplate template = transactionTemplateDao.findById(id).orElse(null);

            if (template == null) {
                throw new EntityNotFoundException(messageProvider.getMessage("error.entityNotFound", TransactionTemplate.class.getSimpleName()));
            }

            if (!currentUser.getRole().equals(User.Role.ADMIN.name()) && !currentUser.getId().equals(template.getOwner().getId())) {
                throw new AccessDeniedException(messageProvider.getMessage("error.accessDenied.modifyOtherUserData"));
            }

            transactionTemplateDao.delete(template);
        }

        @Override
        public void editTemplate(TransactionTemplateDto modifyTemplate) {
            TransactionTemplate template = transactionTemplateDao.findById(modifyTemplate.getId()).orElse(null);

            if (template == null) {
                throw new EntityNotFoundException(messageProvider.getMessage("error.entityNotFound", TransactionTemplate.class.getSimpleName()));
            }

            if (!currentUser.getRole().equals(User.Role.ADMIN.name()) && !currentUser.getId().equals(template.getOwner().getId())) {
                throw new AccessDeniedException(messageProvider.getMessage("error.accessDenied.modifyOtherUserData"));
            }

            template.setTemplateName(modifyTemplate.getTemplateName());
            template.setReceiverAccountNumber(modifyTemplate.getReceiverAccountNumber());
            template.setSenderAccountNumber(modifyTemplate.getSenderAccountNumber());
            template.setSentAmount(modifyTemplate.getSentAmount());
            template.setDueDate(modifyTemplate.getDueDate());
            template.setConstantSymbol(modifyTemplate.getConstantSymbol());
            template.setVariableSymbol(modifyTemplate.getVariableSymbol());
            template.setSpecificSymbol(modifyTemplate.getSpecificSymbol());
            template.setMessage(modifyTemplate.getMessage());
            transactionTemplateDao.save(template);
        }
    }

}
