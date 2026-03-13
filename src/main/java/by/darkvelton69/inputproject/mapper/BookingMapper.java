package by.darkvelton69.inputproject.mapper;

import by.darkvelton69.inputproject.dto.BookingRequest;
import by.darkvelton69.inputproject.dto.BookingResponse;
import by.darkvelton69.inputproject.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(target = "client", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    Booking toEntity(BookingRequest request);

    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "doctor", source = "doctor")
    BookingResponse toResponse(Booking booking);

    List<BookingResponse> toResponseList(List<Booking> booking);

}
