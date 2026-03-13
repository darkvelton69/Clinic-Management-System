package by.darkvelton69.inputproject.repository;

import by.darkvelton69.inputproject.dto.DoctorResponse;
import by.darkvelton69.inputproject.entity.Doctor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @EntityGraph(attributePaths = {"user", "department"})
    Optional<Doctor> findById(Long id);

    Doctor findByUser_Email(String userEmail);
}
