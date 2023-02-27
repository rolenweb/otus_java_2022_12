package ru.otus.l12;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import ru.otus.l12.homework.Banknote;

public class BanknoteTest {
    @Test
    @DisplayName("Test that a banknote can be created")
    public void canCreateBanknote(){
        Banknote banknote = new Banknote(100);
        assertEquals(100, banknote.getValue());
    }

    @Test
    @DisplayName("Test that an exception is thrown is nominal less than zero")
    public void throwExceptionIfNominalLessZero() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
           new Banknote(-1);
        });
        String expectedMessage = "Banknote value must be greater than 0";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
