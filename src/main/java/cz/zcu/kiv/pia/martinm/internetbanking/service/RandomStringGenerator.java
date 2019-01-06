package cz.zcu.kiv.pia.martinm.internetbanking.service;

import java.util.Random;

/**
 * Generates strings of any length. Strings can by only numerical or alphanumerical.
 *
 * Date: 25.12.2018
 *
 * @author Martin Matas
 */
public class RandomStringGenerator {

    /**
     * Defines possible characters for numeric string.
     */
    private static final String NUMBERS = "0123456789";

    /**
     * Defines possible characters for alphanumeric string.
     */
    private static final String CHARACTERS = NUMBERS + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * Generates numeric string of given length.
     *
     * @param length - defines length of string
     * @return generated numeric string
     */
    public static String generateNumeric(int length) {
        return generate(length, NUMBERS);
    }

    /**
     * Generates alphanumeric string of given length.
     *
     * @param length - defines length of string
     * @return generated alphanumeric string
     */
    public static String generateAlphanumeric(int length) {
        return generate(length, CHARACTERS);
    }

    /**
     * Generates new string of given length from given characters (=alphabet of characters).
     *
     * @param length - length of generated string
     * @param alphabet - possible characters
     * @return generated string
     */
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
