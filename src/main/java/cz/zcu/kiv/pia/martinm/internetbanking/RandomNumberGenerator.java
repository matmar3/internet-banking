package cz.zcu.kiv.pia.martinm.internetbanking;

import java.util.Random;

/**
 * Date: 25.12.2018
 *
 * @author Martin Matas
 */
public class RandomNumberGenerator {

    private static final String CHARACTERS = "0123456789";


    public static String generate(int length) {
        Random random;
        StringBuilder builder;

        random = new Random(System.currentTimeMillis());
        builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        return builder.toString();
    }

}
