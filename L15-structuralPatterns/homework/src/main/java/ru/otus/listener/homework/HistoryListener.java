package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    private final HashMap<Long, Message> history = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        var msgBuilder = msg.toBuilder();
        if (msg.getField13() != null) {
            var objectForMessage = new ObjectForMessage();
            List<String> clonedList = new ArrayList<>(msg.getField13().getData());
            objectForMessage.setData(clonedList);
            msgBuilder.field13(objectForMessage);
        }
        history.put(msg.getId(), msgBuilder.build());

    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.of(history.get(id));
    }
}
