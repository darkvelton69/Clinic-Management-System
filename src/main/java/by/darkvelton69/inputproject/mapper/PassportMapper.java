package by.darkvelton69.inputproject.mapper;

import by.darkvelton69.inputproject.dto.PassportRequest;
import by.darkvelton69.inputproject.dto.PassportResponse;
import by.darkvelton69.inputproject.entity.Passport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PassportMapper {

    @Mapping(target = "user", ignore = true)
    Passport toEntity(PassportRequest request);

    PassportResponse toResponse(Passport passport);
}
