package ru.otus.jdbc.mapper;

public class StringUtil {
    public static String capitalize(String inputString) {
        String firstLetter = inputString.substring(0, 1).toUpperCase();
        String restString = inputString.substring(1);

        return String.format("%s%s", firstLetter, restString);
    }
}
