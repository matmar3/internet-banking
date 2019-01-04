package cz.zcu.kiv.pia.martinm.internetbanking.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Date: 02.01.2019
 *
 * @author Martin Matas
 */
@Service
public class DefaultMessageProvider implements MessageProvider {

    private final Locale LOCALE = Locale.ENGLISH;

    private MessageSource messageSource;

    public DefaultMessageProvider(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getMessage(String identifier, Object ...args) {
        if (args.length == 0) args = null;
        return messageSource.getMessage(identifier, args, LOCALE);
    }

}
