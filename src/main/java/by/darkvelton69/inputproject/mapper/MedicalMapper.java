package by.darkvelton69.inputproject.mapper;

import by.darkvelton69.inputproject.dto.MedicalRecordRequest;
import by.darkvelton69.inputproject.dto.MedicalRecordResponse;
import by.darkvelton69.inputproject.entity.MedicalRecord;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MedicalMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    MedicalRecord updateEntityFromDto(MedicalRecordRequest mrr, @MappingTarget MedicalRecord entity);

    MedicalRecord toEntity(MedicalRecordRequest mrr);

    @Mapping(target = "clientId", source = "client.id")
    MedicalRecordResponse toResponse(MedicalRecord mr);

}
