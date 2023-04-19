package ru.otus.cachehw;

public class HwCacheKeyProviderImpl implements HwCacheKeyProvider{
    @Override
    public String getKey(String entity, Long id) {
        return String.format("%s_%s", entity, id);
    }
}
