package cz.zcu.kiv.pia.martinm.internetbanking.service;

import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Date: 28.12.2018
 *
 * @author Martin Matas
 */
@Service
public class DefaultTuringTestProvider implements TuringTestProvider {

    private Map<Integer, TuringTest> activeTests;

    public DefaultTuringTestProvider() {
        activeTests = new HashMap<>();
    }

    @Override
    public TuringTest generateRandomTest() {
        Random r = new Random(System.currentTimeMillis());
        int index;

        do {
            index = r.nextInt(Integer.MAX_VALUE);
        } while (activeTests.containsKey(index));
        int a = r.nextInt(10);
        int b = r.nextInt(9) + 1;

        TuringTest newTest = new DefaultTuringTest(
                index,
                String.format("Kolik je %d + %d?", a, b),
                (a + b)
        );
        activeTests.put(index, newTest);
        return newTest;
    }

    @Override
    public boolean testAnswer(Integer testId, Integer answer) {
        if (testId == null || answer == null || !activeTests.containsKey(testId)) {
            return false;
        }

        TuringTest test = activeTests.get(testId);
        boolean isCorrect = test.isCorrect(answer);

        activeTests.remove(testId);
        return isCorrect;
    }
}
