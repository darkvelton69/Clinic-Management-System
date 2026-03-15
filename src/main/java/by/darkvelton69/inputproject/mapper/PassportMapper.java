package by.darkvelton69.inputproject.mapper;

import by.darkvelton69.inputproject.dto.PassportRequest;
import by.darkvelton69.inputproject.dto.PassportResponse;
import by.darkvelton69.inputproject.entity.Passport;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PassportMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Passport updateEntityFromDto(PassportRequest passportRequest, @MappingTarget Passport passport);

    @Mapping(target = "user", ignore = true)
    Passport toEntity(PassportRequest passportRequest);

    PassportResponse toResponse(Passport passport);


}
