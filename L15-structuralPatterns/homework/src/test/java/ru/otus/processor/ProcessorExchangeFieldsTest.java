package ru.otus.processor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProcessorExchangeFieldsTest {
    @Test
    @DisplayName("Test the processor that exchange fields")
    void canExchangeField() {
        var id = 100L;
        String field11 = "field11";
        String field12 = "field12";
        Message message = new Message
                .Builder(id)
                .field11(field11)
                .field12(field12)
                .build();

        Processor processor = new ProcessorExchangeFields();
        message = processor.process(message);
        assertEquals(field11, message.getField12());
        assertEquals(field12, message.getField11());
    }
}
