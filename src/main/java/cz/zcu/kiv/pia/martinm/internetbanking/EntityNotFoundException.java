package cz.zcu.kiv.pia.martinm.internetbanking;

/**
 * Exception means that {@link cz.zcu.kiv.pia.martinm.internetbanking.domain.DataTransferObject} not found.
 *
 * Date: 02.01.2019
 *
 * @author Martin Matas
 */
public class EntityNotFoundException extends RuntimeException {

    /**
     * {@link cz.zcu.kiv.pia.martinm.internetbanking.domain.DataTransferObject} not found.
     *
     * @param message - exception message
     */
    public EntityNotFoundException(String message) {
        super(message);
    }

}
