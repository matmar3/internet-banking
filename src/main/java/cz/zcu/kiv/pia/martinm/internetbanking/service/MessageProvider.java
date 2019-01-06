package cz.zcu.kiv.pia.martinm.internetbanking.service;

/**
 * Provides method to access messages.
 *
 * Date: 02.01.2019
 *
 * @author Martin Matas
 */
public interface MessageProvider {

    /**
     * Finds message in properties by given identification key. Can contains additional arguments
     * that are used together with parametrized messages e.g. '{0} + {1} = 5', where 0 and 1 are arguments.
     *
     * @param identifier - identification key e.g. error.404.heading
     * @param args - additional arguments to parametrized message
     * @return founded message / composed message
     */
    String getMessage(String identifier, Object ...args);

}
