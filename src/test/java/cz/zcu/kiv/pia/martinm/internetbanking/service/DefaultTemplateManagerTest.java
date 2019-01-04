package cz.zcu.kiv.pia.martinm.internetbanking.service;

import cz.zcu.kiv.pia.martinm.internetbanking.EntityNotFoundException;
import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.TransactionTemplateDto;
import cz.zcu.kiv.pia.martinm.internetbanking.dao.TransactionTemplateDao;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.TransactionTemplate;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Date: 04.01.2019
 *
 * @author Martin Matas
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class DefaultTemplateManagerTest {

    @MockBean
    private TransactionTemplateDao templateDao;

    @MockBean
    private MessageProvider messageProvider;

    private DefaultTemplateManager templateManager;

    private User admin, customer, customer2;

    @Before
    public void setUp() {
        templateManager = new DefaultTemplateManager(templateDao, messageProvider);

        admin = new User("Jan", "Kratochvíl", "870515/2213", "kratochvil.jan@gmail.com");
        admin.setId(1);
        admin.setRole(User.Role.ADMIN.name());
        admin.setEnabled(true);

        customer = new User("Brad", "Pitt", "630913/1985", "brad@pitt.com");
        customer.setId(2);
        customer.setRole(User.Role.CUSTOMER.name());

        customer2 = new User("Filip", "Král", "940815/2118", "kral.filip@gmail.com");
        customer2.setId(3);
        customer2.setRole(User.Role.CUSTOMER.name());
    }

    @Test
    public void createTemplate_AsAdmin() {
        AuthorizedTemplateManager atm = templateManager.authorize(admin);

        when(templateDao.save(any(TransactionTemplate.class))).thenReturn(any(TransactionTemplate.class));
        atm.createTemplate(new TransactionTemplateDto(), customer);
        verify(templateDao, times(1)).save(any(TransactionTemplate.class));
    }

    @Test
    public void createTemplate_forYourself() {
        AuthorizedTemplateManager atm = templateManager.authorize(customer);

        when(templateDao.save(any(TransactionTemplate.class))).thenReturn(any(TransactionTemplate.class));
        atm.createTemplate(new TransactionTemplateDto(), customer);
        verify(templateDao, times(1)).save(any(TransactionTemplate.class));
    }

    @Test(expected = AccessDeniedException.class)
    public void createTemplate_forSomeoneElse() {
        AuthorizedTemplateManager atm = templateManager.authorize(customer);
        atm.createTemplate(new TransactionTemplateDto(), customer2);
    }

    @Test
    public void findAllTemplatesByUser_asAdmin() {
        AuthorizedTemplateManager atm = templateManager.authorize(admin);

        when(templateDao.findAllByOwner(customer)).thenReturn(new ArrayList<>());
        atm.findAllTemplatesByUser(customer);
        verify(templateDao, times(1)).findAllByOwner(customer);
    }

    @Test
    public void findAllTemplatesByUser_asCustomerForYourself() {
        AuthorizedTemplateManager atm = templateManager.authorize(customer);
        final TransactionTemplate template = new TransactionTemplate(
                "test1", "2010304050/1000", new BigDecimal(12), "1020304050/2700", new Date(), "", "", "", "", customer
        );
        List<TransactionTemplate> templates = new ArrayList<>();
        templates.add(template);

        when(templateDao.findAllByOwner(customer)).thenReturn(templates);

        List<TransactionTemplate> result = atm.findAllTemplatesByUser(customer);
        assertThat(result, hasSize(1));
        assertThat(result.get(0), is(equalTo(template)));

        verify(templateDao, times(1)).findAllByOwner(customer);
    }

    @Test(expected = AccessDeniedException.class)
    public void findAllTemplatesByUser_asCustomerForSomeoneElse() {
        AuthorizedTemplateManager atm = templateManager.authorize(customer);

        when(templateDao.findAllByOwner(customer2)).thenReturn(new ArrayList<>());
        atm.findAllTemplatesByUser(customer2);
        verify(templateDao, times(1)).findAllByOwner(customer2);
    }

    @Test
    public void findTemplateById_asAdmin() {
        AuthorizedTemplateManager atm = templateManager.authorize(admin);
        final TransactionTemplate template = new TransactionTemplate(
                "test1", "2010304050/1000", new BigDecimal(12), "1020304050/2700", new Date(), "", "", "", "", customer
        );
        final Integer templateId = 1;

        when(templateDao.findByOwnerAndId(customer, templateId)).thenReturn(template);

        TransactionTemplate result = atm.findTemplateById(customer, templateId);
        assertThat(result, is(equalTo(template)));

        verify(templateDao, times(1)).findByOwnerAndId(customer, templateId);
    }

    @Test(expected = EntityNotFoundException.class)
    public void findTemplateById_templateNotExists() {
        AuthorizedTemplateManager atm = templateManager.authorize(customer);
        final Integer templateId = 1;

        when(templateDao.findByOwnerAndId(customer, templateId)).thenReturn(null);
        atm.findTemplateById(customer, templateId);
        verify(templateDao, times(1)).findByOwnerAndId(customer, templateId);
    }

    @Test(expected = AccessDeniedException.class)
    public void findTemplateById_asCustomerForSomeoneElse() {
        AuthorizedTemplateManager atm = templateManager.authorize(customer);
        final Integer templateId = 1;
        atm.findTemplateById(customer2, templateId);
    }

    @Test
    public void removeTemplate_asAdmin() {
        AuthorizedTemplateManager atm = templateManager.authorize(admin);
        final TransactionTemplate template = new TransactionTemplate(
                "test1", "2010304050/1000", new BigDecimal(12), "1020304050/2700", new Date(), "", "", "", "", customer
        );
        final Integer templateId = 1;

        when(templateDao.findById(templateId)).thenReturn(Optional.of(template));

        atm.removeTemplate(templateId);

        verify(templateDao, times(1)).delete(template);
        verify(templateDao, times(1)).findById(templateId);
    }

    @Test(expected = AccessDeniedException.class)
    public void removeTemplateOfSomeoneElse_asCustomer() {
        AuthorizedTemplateManager atm = templateManager.authorize(customer);
        final TransactionTemplate template = new TransactionTemplate(
                "test1", "2010304050/1000", new BigDecimal(12), "1020304050/2700", new Date(), "", "", "", "", customer2
        );
        final Integer templateId = 1;

        when(templateDao.findById(templateId)).thenReturn(Optional.of(template));
        atm.removeTemplate(templateId);
        verify(templateDao, times(1)).findById(templateId);
    }

    @Test(expected = EntityNotFoundException.class)
    public void removeTemplate_asCustomer_templateNotExists() {
        AuthorizedTemplateManager atm = templateManager.authorize(customer);
        final Integer templateId = 1;

        when(templateDao.findById(templateId)).thenReturn(Optional.empty());
        atm.removeTemplate(templateId);
        verify(templateDao, times(1)).findById(templateId);
    }

    @Test
    public void editTemplate_asAdmin() {
        AuthorizedTemplateManager atm = templateManager.authorize(admin);
        final TransactionTemplate template = new TransactionTemplate(
                "test1", "2010304050/1000", new BigDecimal(12), "1020304050/2700", new Date(), "", "", "", "", customer2
        );
        final Integer templateId = 1;

        TransactionTemplateDto modifiedTemplate = new TransactionTemplateDto();
        modifiedTemplate.setId(templateId);
        modifiedTemplate.setTemplateName(template.getTemplateName());
        modifiedTemplate.setReceiverAccountNumber(template.getReceiverAccountNumber());
        modifiedTemplate.setSenderAccountNumber(template.getSenderAccountNumber());
        modifiedTemplate.setSentAmount(template.getSentAmount());

        when(templateDao.findById(templateId)).thenReturn(Optional.of(template));
        when(templateDao.save(template)).thenReturn(template);

        atm.editTemplate(modifiedTemplate);

        verify(templateDao, times(1)).findById(templateId);
        verify(templateDao, times(1)).save(template);
    }

    @Test(expected = EntityNotFoundException.class)
    public void editTemplate_templateNotExists() {
        AuthorizedTemplateManager atm = templateManager.authorize(customer);
        final Integer templateId = 1;

        TransactionTemplateDto modifiedTemplate = new TransactionTemplateDto();
        modifiedTemplate.setId(templateId);

        when(templateDao.findById(templateId)).thenReturn(Optional.empty());
        atm.editTemplate(modifiedTemplate);
        verify(templateDao, times(1)).findById(templateId);
    }

    @Test(expected = AccessDeniedException.class)
    public void editTemplateOfSomeoneElse_asCustomer() {
        AuthorizedTemplateManager atm = templateManager.authorize(customer);
        final TransactionTemplate template = new TransactionTemplate(
                "test1", "2010304050/1000", new BigDecimal(12), "1020304050/2700", new Date(), "", "", "", "", customer2
        );
        final Integer templateId = 1;

        TransactionTemplateDto modifiedTemplate = new TransactionTemplateDto();
        modifiedTemplate.setId(templateId);

        when(templateDao.findById(templateId)).thenReturn(Optional.of(template));
        atm.editTemplate(modifiedTemplate);
        verify(templateDao, times(1)).findById(templateId);
    }

}