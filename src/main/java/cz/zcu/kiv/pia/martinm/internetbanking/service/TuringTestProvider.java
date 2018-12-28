package cz.zcu.kiv.pia.martinm.internetbanking.service;

/**
 * Date: 28.12.2018
 *
 * @author Martin Matas
 */
public interface TuringTestProvider {

    TuringTest generateRandomTest();

    boolean testAnswer(Integer testId, Integer answer);

}
