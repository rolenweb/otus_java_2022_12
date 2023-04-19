package ru.otus.cachehw;

public interface HwCacheKeyProvider {
    String getKey(String entity, Long id);
}
