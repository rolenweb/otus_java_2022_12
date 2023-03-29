package ru.otus.jdbc.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class StringUtilTest {

    @Test
    @DisplayName("A string should be capitalized")
    void stringShouldBeCapitalized() {
        var str = "test";
        assertEquals("Test", StringUtil.capitalize(str));
    }
}
