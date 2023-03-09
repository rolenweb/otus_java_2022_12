package ru.otus.processor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ProcessorThrowExceptionTest {

    @Test
    @DisplayName("Throw an exception on an even second")
    void throwException() {
        Processor processor = new ProcessorThrowException();
        var id = 100L;
        Message message = new Message.Builder(id).build();
        assertThatExceptionOfType(EvenNumberException.class).isThrownBy(() -> processor.process(message));
    }
}
