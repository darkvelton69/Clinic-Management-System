package by.darkvelton69.inputproject.mapper;

import by.darkvelton69.inputproject.dto.BookingResponse;
import by.darkvelton69.inputproject.dto.DepartmentRequest;
import by.darkvelton69.inputproject.dto.DepartmentResponse;
import by.darkvelton69.inputproject.entity.Booking;
import by.darkvelton69.inputproject.entity.Department;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    Department toEntity(DepartmentRequest request);

    DepartmentResponse toResponse(Department department);

}
