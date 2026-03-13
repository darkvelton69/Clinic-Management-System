package by.darkvelton69.inputproject.mapper;

import by.darkvelton69.inputproject.dto.DoctorRegistration;
import by.darkvelton69.inputproject.dto.DoctorResponse;
import by.darkvelton69.inputproject.entity.Doctor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DocUserDepMapper {

    Doctor toEntity(DoctorRegistration doctorRegistration);

    DoctorResponse toResponse(Doctor doctor);
}
