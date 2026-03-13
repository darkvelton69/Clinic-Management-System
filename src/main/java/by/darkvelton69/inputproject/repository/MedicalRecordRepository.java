package by.darkvelton69.inputproject.repository;

import by.darkvelton69.inputproject.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    Optional<MedicalRecord> findByClientUserEmail(String clientUserEmail);
}
