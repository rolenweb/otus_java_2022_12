package ru.otus.cachehw;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Consumer;

public class MyCache<K, V> implements HwCache<K, V> {
//Надо реализовать эти методы
    private final Map<K, V> cache = new WeakHashMap<>();
    private final List<HwListener<K, V>> listeners = new ArrayList<>();

    private static final Logger logger = LoggerFactory.getLogger(MyCache.class);

    private Consumer<HwListener<K, V>> exceptionWrapper(Consumer<HwListener<K, V>> listener) {
        return i -> {
            try {
                listener.accept(i);
            } catch (RuntimeException exception) {
                logger.error(exception.toString());
            }
        };
    }

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
        listeners.forEach(exceptionWrapper(kvHwListener -> kvHwListener.notify(key, value, "put")));
    }

    @Override
    public void remove(K key) {
        var value = get(key);
        listeners.forEach(exceptionWrapper(kvHwListener -> kvHwListener.notify(key, value, "remove")));
        cache.remove(key);
    }

    @Override
    public V get(K key) {
        var value = cache.get(key);
        listeners.forEach(exceptionWrapper(kvHwListener -> kvHwListener.notify(key, value, "get")));
        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }
}
