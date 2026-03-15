package by.darkvelton69.inputproject.repository;

import by.darkvelton69.inputproject.entity.Booking;
import by.darkvelton69.inputproject.entity.Condition;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @EntityGraph(attributePaths = {"client","doctor"})
    Optional<Booking> findById(Long id);

    @EntityGraph(attributePaths = {"client", "doctor.department"})
    List<Booking> findAll();

    @EntityGraph(attributePaths = {"doctor"})
    List<Booking> findAllByClient_Id(Long clientId);


    List<Booking> findAllByDoctorIdAndCondition(Long doctorId, Condition condition);

    Booking findByIdAndClient_User_Email(Long id, String clientUserEmail);

}
