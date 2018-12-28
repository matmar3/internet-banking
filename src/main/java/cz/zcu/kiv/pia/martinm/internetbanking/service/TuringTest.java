package cz.zcu.kiv.pia.martinm.internetbanking.service;

/**
 * Date: 28.12.2018
 *
 * @author Martin Matas
 */
public interface TuringTest {

    int getId();

    String getQuestion();

    boolean isCorrect(int answer);

}
