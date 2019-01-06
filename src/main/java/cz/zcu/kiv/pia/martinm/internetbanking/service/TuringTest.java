package cz.zcu.kiv.pia.martinm.internetbanking.service;

/**
 * Defines methods to access data of generated test.
 *
 * Date: 28.12.2018
 *
 * @author Martin Matas
 */
public interface TuringTest {

    /**
     * Returns identifier of this test.
     *
     * @return turing test ID
     */
    int getId();

    /**
     * Returns generated question of this test.
     *
     * @return generated question
     */
    String getQuestion();

    /**
     * Compares answer with stored question of this test.
     *
     * @param answer - possible answer
     * @return if answer is correct
     */
    boolean isCorrect(int answer);

}
