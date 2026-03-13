package by.darkvelton69.inputproject.mapper;

import by.darkvelton69.inputproject.dto.ClientResponse;
import by.darkvelton69.inputproject.entity.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientResponse toResponse(Client client);

}
