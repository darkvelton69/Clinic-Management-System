package by.darkvelton69.inputproject.controller;

import by.darkvelton69.inputproject.dto.ClientResponse;
import by.darkvelton69.inputproject.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/polyclinic34/profile")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping("/{id}")
    public ClientResponse getClient(@PathVariable Long id){
        return clientService.getClient(id);
    }

}
