package com.exsoft.momedumerchant.util;

public abstract class NumberUtils extends org.springframework.util.NumberUtils {
    public static Integer tryParse(String text, Integer defaultVal) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return defaultVal;
        }

    }

    public static Integer tryParse(String text) {
        return NumberUtils.tryParse(text, null);
    }

    public static boolean isNumeric(final String str) {
        if (str == null || str.length() == 0) {
            return false;
        }


        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }

        }

        return true;
    }

}
