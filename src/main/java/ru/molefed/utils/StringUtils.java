package ru.molefed.utils;

public class StringUtils {

    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0 || s.trim().length() == 0;
    }

}
