package framework.helpers;

public class StringHelper {
    public static String getNumericValueFromString(String string) {
        return string.replaceAll("[^0-9]", "");
    }
}