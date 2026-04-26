package by.darkvelton69.inputproject.repository;

import by.darkvelton69.inputproject.entity.Booking;
import by.darkvelton69.inputproject.entity.Condition;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.sql.Time;
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



    @Query(value = """
        WITH shift_slots AS (
            SELECT CAST(
                generate_series(
                    ws.shift_date + ws.start_time,
                    ws.shift_date + ws.end_time - CAST(:intervalStr AS interval) ,
                    CAST(:intervalStr AS interval)
                ) AS time
            ) AS slot_time
            FROM work_shifts ws
            WHERE ws.doctor_id = :doctorId AND ws.shift_date = :date
        )
        SELECT slot_time FROM shift_slots
        WHERE slot_time NOT IN (
            SELECT b.appointment_time FROM bookings b
            WHERE b.doctor_id = :doctorId
              AND b.appointment_date = :date
              AND b.condition = 'ACTIVE'
        )
        ORDER BY slot_time
    """, nativeQuery = true)
    List<Time> findAvailableSlotsByDb(
            @Param("doctorId") Long doctorId,
            @Param("date") LocalDate date,
            @Param("intervalStr") String intervalStr
    );

}
