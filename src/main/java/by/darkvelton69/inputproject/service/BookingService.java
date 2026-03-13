package by.darkvelton69.inputproject.service;

import by.darkvelton69.inputproject.dto.BookingRequest;
import by.darkvelton69.inputproject.dto.BookingResponse;
import by.darkvelton69.inputproject.entity.Booking;
import by.darkvelton69.inputproject.entity.Client;
import by.darkvelton69.inputproject.entity.Condition;
import by.darkvelton69.inputproject.entity.Doctor;
import by.darkvelton69.inputproject.exception.BookingClosedException;
import by.darkvelton69.inputproject.exception.NotFoundException;
import by.darkvelton69.inputproject.mapper.BookingMapper;
import by.darkvelton69.inputproject.repository.BookingRepository;
import by.darkvelton69.inputproject.repository.ClientRepository;
import by.darkvelton69.inputproject.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingService {
    private final BookingRepository bookingRepository;
    private final ClientRepository clientRepository;
    private final BookingMapper bookingMapper;
    private final DoctorRepository doctorRepository;


    @Transactional
    public BookingResponse record(BookingRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Client client = clientRepository.findByUser_Email(email).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        Doctor doctor = doctorRepository.findById(request.doctorId()).orElseThrow(() -> new NotFoundException("Врач не найден"));

        Booking booking = bookingMapper.toEntity(request);

        booking.setClient(client);
        booking.setDoctor(doctor);

        Booking savedBooking = bookingRepository.save(booking);

        return bookingMapper.toResponse(savedBooking);
    }

    public BookingResponse getBooking(Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new NotFoundException("Запись не найдена"));
        return bookingMapper.toResponse(booking);

    }

    public List<BookingResponse> getAllBookingClient(Long id) {
        List<Booking> booking = bookingRepository.findAllByClient_Id(id);

        return bookingMapper.toResponseList(booking);
    }

    public List<BookingResponse> getMySchedule(Long id){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Doctor doctor = doctorRepository.findByUser_Email(email);

        if(!id.equals(doctor.getId())){
            throw new AccessDeniedException("Вы не имеете прав смотреть записи другого врача");
        }

        return bookingRepository.findAllByDoctorIdAndCondition(doctor.getId(), Condition.ACTIVE)
                .stream()
                .map(bookingMapper::toResponse)
                .toList();


    }

    @Transactional
    public void closeBooking(Long id) {


        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Бронь не найдена"));



        if (booking.getCondition() == Condition.CLOSE) {
            throw new BookingClosedException("Бронь уже закрыта");
        }

        booking.setCondition(Condition.CLOSE);
    }
}
