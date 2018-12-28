package cz.zcu.kiv.pia.martinm.internetbanking.service;

/**
 * Date: 28.12.2018
 *
 * @author Martin Matas
 */
public class DefaultTuringTest implements TuringTest {

    private int id;
    private String question;
    private int correctAnswer;

    public DefaultTuringTest(int id, String question, int correctAnswer) {
        this.id = id;
        this.question = question;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public boolean isCorrect(int answer) {
        return (answer == correctAnswer);
    }

}
