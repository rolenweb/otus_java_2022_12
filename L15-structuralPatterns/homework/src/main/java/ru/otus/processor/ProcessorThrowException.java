package ru.otus.processor;

import ru.otus.model.Message;
import java.time.LocalDateTime;

public class ProcessorThrowException implements Processor {
    @Override
    public Message process(Message message) {
        int startSecond = LocalDateTime.now().getSecond();
        int second = 0;
        while (second < 10) {
            if (LocalDateTime.now().getSecond() - startSecond != second) {
                second = LocalDateTime.now().getSecond() - startSecond;
                if ( second % 2 == 0 ){
                    throw new EvenNumberException();
                }
            }
        }
        return null;
    }
}
