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
        var field11 = "field11";
        var field12 = "field12";
        var message = new Message
                .Builder(id)
                .field11(field11)
                .field12(field12)
                .build();

        Processor processor = new ProcessorExchangeFields();
        var updatedMessage = processor.process(message);
        assertEquals(field11, updatedMessage.getField12());
        assertEquals(field12, updatedMessage.getField11());
    }
}
