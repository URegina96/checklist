package com.example.checklist;

public class NumberUtils {
    public static boolean isNumber(String value) {
        boolean isNumber = true;
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {
            isNumber = false;
        }
        return isNumber;
    }

    public static int stringToInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
