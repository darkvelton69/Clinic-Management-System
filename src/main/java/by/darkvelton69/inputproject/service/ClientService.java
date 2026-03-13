package by.darkvelton69.inputproject.service;

import by.darkvelton69.inputproject.dto.ClientResponse;
import by.darkvelton69.inputproject.entity.Client;
import by.darkvelton69.inputproject.exception.NotFoundException;
import by.darkvelton69.inputproject.mapper.ClientMapper;
import by.darkvelton69.inputproject.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientResponse getClient(Long id){
        Client client = clientRepository.findById(id).
                orElseThrow(()-> new NotFoundException("Пациент не найден"));

        return clientMapper.toResponse(client);
    }
}
