package by.darkvelton69.inputproject.mapper;

import by.darkvelton69.inputproject.dto.WorkShiftRequest;
import by.darkvelton69.inputproject.dto.WorkShiftResponse;
import by.darkvelton69.inputproject.entity.WorkShift;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkShiftMapper {

    WorkShift toEntity(WorkShiftRequest workShiftRequest);

    @Mapping(target = "doctor", source = "doctor")
    WorkShiftResponse toResponse(WorkShift workShift);

}
