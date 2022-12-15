package framework.helpers;

import java.util.Random;

public class RandomHelper {
    public static final int RANDOM_STRING_LENGTH = 10;
    private static final Random RANDOM = new Random();
    private static final int FIRST_ENGLISH_LETTER_UNICODE = 'a';
    private static final int LAST_ENGLISH_LETTER_UNICODE = 'z';

    public static int generateInt(int origin, int bound) {
        return RANDOM.nextInt(origin, bound);
    }

    public static String getRandomString(int length) {
        return generateRandomString(length);
    }

    private static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(generateRandomChar());
        }
        return sb.toString();
    }

    private static char generateRandomChar() {
        return (char) (generateInt(FIRST_ENGLISH_LETTER_UNICODE, LAST_ENGLISH_LETTER_UNICODE + 1));
    }
}