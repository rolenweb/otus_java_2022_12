package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.domain.Address;
import ru.otus.domain.Client;
import ru.otus.domain.Phone;
import ru.otus.form.ClientForm;
import ru.otus.generators.IdGenerator;
import ru.otus.service.ClientService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ClientController {
    private final ClientService clientService;
    private final IdGenerator idGenerator;

    public ClientController(ClientService clientService, IdGenerator idGenerator) {
        this.clientService = clientService;
        this.idGenerator = idGenerator;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Client> clients = clientService.findAll();
        model.addAttribute("clients", clients);
        return "clientsList";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("client", new ClientForm());
        return "clientCreate";
    }

    @PostMapping("/save")
    public RedirectView save(@ModelAttribute ClientForm clientForm) {
        var clientId = idGenerator.generate();
        clientService.save(
                new Client(
                        clientId,
                        clientForm.getName(),
                        new Address(clientId, clientForm.getStreet()),
                        clientForm.getPhones().stream().map(number -> new Phone(number, clientId)).collect(Collectors.toSet()),
                        true
                )
        );
        return new RedirectView("/", true);
    }
}
