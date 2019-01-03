package cz.zcu.kiv.pia.martinm.internetbanking.service;

import java.util.Random;

/**
 * Date: 25.12.2018
 *
 * @author Martin Matas
 */
public class RandomStringGenerator {

    private static final String NUMBERS = "0123456789";

    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String generateNumeric(int length) {
        return generate(length, NUMBERS);
    }

    public static String generateAlphanumeric(int length) {
        return generate(length, CHARACTERS);
    }

    private static String generate(final int length, final String alphabet) {
        Random random;
        StringBuilder builder;

        random = new Random(System.currentTimeMillis());
        builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }

        return builder.toString();
    }

}
