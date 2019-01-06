package cz.zcu.kiv.pia.martinm.internetbanking.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Locale;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link DefaultMessageProvider}.
 *
 * Date: 04.01.2019
 *
 * @author Martin Matas
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class DefaultMessageProviderTest {

    @MockBean
    private MessageSource messageSource;

    private DefaultMessageProvider messageProvider;

    @Before
    public void setUp() {
        messageProvider = new DefaultMessageProvider(messageSource);
    }

    @Test
    public void getMessage() {
        final String key = "test.key";
        final String value = "TEXT";

        when(messageSource.getMessage(key, null, Locale.ENGLISH)).thenReturn(value);
        String result = messageProvider.getMessage(key);
        assertThat(result, is(equalTo(value)));
        verify(messageSource, times(1)).getMessage(key, null, Locale.ENGLISH);
    }

    @Test
    public void getParametrizedMessage() {
        final String key = "test.key"; // '{0} + {1} = Z'
        final String value = "X + Y = Z";
        final Object[] args = new Object[]{"X", "Y"};
        final String arg1 = "X";
        final String arg2 = "Y";

        when(messageSource.getMessage(key, args, Locale.ENGLISH)).thenReturn(value);
        String result = messageProvider.getMessage(key, arg1, arg2);
        assertThat(result, is(equalTo(value)));
        verify(messageSource, times(1)).getMessage(key, args, Locale.ENGLISH);
    }

}