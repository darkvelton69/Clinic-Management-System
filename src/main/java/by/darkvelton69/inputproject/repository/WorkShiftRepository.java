package by.darkvelton69.inputproject.repository;

import by.darkvelton69.inputproject.entity.WorkShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface WorkShiftRepository extends JpaRepository<WorkShift, Long> {

    Optional<WorkShift> findByDoctorIdAndShiftDate(Long doctorId, LocalDate date);

}
