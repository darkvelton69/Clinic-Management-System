package by.darkvelton69.inputproject.repository;

import by.darkvelton69.inputproject.entity.Booking;
import by.darkvelton69.inputproject.entity.Condition;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @EntityGraph(attributePaths = {"client.user","doctor.user"})
    Optional<Booking> findById(Long id);

    @EntityGraph(attributePaths = {"client", "doctor.department"})
    List<Booking> findAll();

    @EntityGraph(attributePaths = {"doctor"})
    List<Booking> findAllByClient_Id(Long clientId);


    List<Booking> findAllByDoctorIdAndCondition(Long doctorId, Condition condition);

    Booking findByIdAndClient_User_Email(Long id, String clientUserEmail);

    List<Booking> findByDoctorIdAndAppointmentDate(Long doctorId, LocalDate date);

    List<Booking> findByClient_TelegramChatIdAndConditionOrderByAppointmentDateAscAppointmentTimeAsc(
            Long telegramChatId,
            Condition condition
    );

    List<Booking> findByDoctorIdAndAppointmentDateAndCondition(
            Long doctorId,
            LocalDate date,
            Condition condition
    );

    @EntityGraph(attributePaths = {"client.user","doctor.user"})
    List<Booking> findByAppointmentDateAndAppointmentTimeAndCondition(
            LocalDate date,
            LocalTime time,
            Condition condition
    );

}
