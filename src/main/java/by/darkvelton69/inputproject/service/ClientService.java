package by.darkvelton69.inputproject.service;

import by.darkvelton69.inputproject.dto.ClientResponse;
import by.darkvelton69.inputproject.entity.Client;
import by.darkvelton69.inputproject.entity.User;
import by.darkvelton69.inputproject.exception.NotFoundException;
import by.darkvelton69.inputproject.mapper.ClientMapper;
import by.darkvelton69.inputproject.repository.ClientRepository;
import by.darkvelton69.inputproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    @Transactional(readOnly = true)
    public Optional<Client> findByTelegramChatId(long chatId){
        return clientRepository.findByTelegramChatId(chatId);
    }

    @Transactional
    public boolean linkTelegramAccount(long chatId, String phone){
        String cleanPhone = phone.replaceAll("[^0-9+]", "");

        Optional<Client> clientOpt = clientRepository.findByPhone(cleanPhone);

        if(clientOpt.isPresent()){

            Client client = clientOpt.get();
            client.setTelegramChatId(chatId);

            clientRepository.save(client);
            return true;
        }else{
            return false;
        }

    }
}
