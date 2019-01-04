package cz.zcu.kiv.pia.martinm.internetbanking.service;

/**
 * Date: 02.01.2019
 *
 * @author Martin Matas
 */
public interface MessageProvider {

    String getMessage(String identifier, Object ...args);

}
