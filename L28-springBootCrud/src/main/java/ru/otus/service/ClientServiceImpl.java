package ru.otus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.domain.Client;
import ru.otus.repository.ClientRepository;
import ru.otus.sessionmanager.TransactionManager;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService{
    public static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);
    private final TransactionManager transactionManager;
    private final ClientRepository clientRepository;

    public ClientServiceImpl(TransactionManager transactionManager, ClientRepository clientRepository) {
        this.transactionManager = transactionManager;
        this.clientRepository = clientRepository;
    }

    @Override
    public Client save(Client client) {
        return transactionManager.doInTransaction(() -> {
            var savedClient = clientRepository.save(client);
            log.info("saved client: {}", savedClient);
            return savedClient;
        });
    }

    @Override
    public List<Client> findAll() {
        var clientList = new ArrayList<Client>();
        clientRepository.findAll().forEach(clientList::add);
        log.info("clientList:{}", clientList);
        return clientList;
    }
}
