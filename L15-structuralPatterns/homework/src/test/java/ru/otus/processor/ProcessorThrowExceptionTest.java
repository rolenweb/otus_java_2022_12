package ru.otus.processor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProcessorThrowExceptionTest {

    @Test
    @DisplayName("Throw an exception on an even second")
    void throwException() {
        var processor = new ProcessorThrowException();
        var id = 100L;
        var message = new Message.Builder(id).build();
        assertThatExceptionOfType(EvenNumberException.class).isThrownBy(() -> processor.process(message));
        var finishSecond = processor.getEvenSecond();
        assertEquals(2,finishSecond);
    }
}
