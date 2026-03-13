package by.darkvelton69.inputproject.controller;

import by.darkvelton69.inputproject.dto.BookingRequest;
import by.darkvelton69.inputproject.dto.BookingResponse;
import by.darkvelton69.inputproject.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/polyclinic34/booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;


    @PostMapping("/record")
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse record(@Valid @RequestBody BookingRequest request){
        return bookingService.record(request);
    }

    @GetMapping("/{id}/doctor")
    public List<BookingResponse> getMySchedule(@PathVariable Long id){
        return bookingService.getMySchedule(id);
    }

    @GetMapping("/{id}")
    public BookingResponse getBooking(@PathVariable Long id){
        return bookingService.getBooking(id);
    }

    @PatchMapping("/{id}/close")
    public ResponseEntity<Void> closeBooking(@PathVariable Long id){
        bookingService.closeBooking(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/allBook")
    public List<BookingResponse> getAllByClientId(@PathVariable Long id){
        return bookingService.getAllBookingClient(id);
    }

}
