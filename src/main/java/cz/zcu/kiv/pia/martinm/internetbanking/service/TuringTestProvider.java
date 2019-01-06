package cz.zcu.kiv.pia.martinm.internetbanking.service;

/**
 * Provides methods to manage turing tests.
 *
 * Date: 28.12.2018
 *
 * @author Martin Matas
 */
public interface TuringTestProvider {

    /**
     * Creates new randomly generated turing test that can be later
     * easily found and compared against given answer.
     *
     * @return new turing test
     */
    TuringTest generateRandomTest();

    /**
     * Finds turing test by given test ID and checks if given answer is correct.
     *
     * @param testId - identifier of turing test, based on this number the test can be found
     * @param answer - answer to turning test question
     * @return if answer is correct
     */
    boolean testAnswer(Integer testId, Integer answer);

}
