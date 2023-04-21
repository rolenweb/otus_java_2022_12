package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.HwCacheKeyProvider;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.sessionmanager.TransactionManager;
import ru.otus.crm.model.Client;

import java.util.List;
import java.util.Optional;

public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionManager transactionManager;

    private final HwCache<String, Client> cache;

    private final HwCacheKeyProvider cacheKeyProvider;

    public DbServiceClientImpl(TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate, HwCache<String, Client> cache, HwCacheKeyProvider cacheKeyProvider) {
        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientDataTemplate;
        this.cache = cache;
        this.cacheKeyProvider = cacheKeyProvider;
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(session -> {
            var clientCloned = client.clone();
            if (client.getId() == null) {
                clientDataTemplate.insert(session, clientCloned);
                log.info("created client: {}", clientCloned);
                cache.put(cacheKeyProvider.getKey("client", clientCloned.getId()), clientCloned);
                return clientCloned;
            }
            clientDataTemplate.update(session, clientCloned);
            log.info("updated client: {}", clientCloned);
            cache.put(cacheKeyProvider.getKey("client", clientCloned.getId()), clientCloned);
            return clientCloned;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        return transactionManager.doInReadOnlyTransaction(session -> {
            String cacheKey = cacheKeyProvider.getKey("client", id);
            Optional<Client> clientOptional;
            if (cache.get(cacheKey) == null) {
                clientOptional = clientDataTemplate.findById(session, id);
                clientOptional.ifPresent(client -> cache.put(cacheKey, client));
            }
            return Optional.ofNullable(cache.get(cacheKey));
        });
    }

    @Override
    public List<Client> findAll() {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientList = clientDataTemplate.findAll(session);
            log.info("clientList:{}", clientList);
            return clientList;
       });
    }
}
