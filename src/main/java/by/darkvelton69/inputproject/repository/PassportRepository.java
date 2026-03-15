package by.darkvelton69.inputproject.repository;

import by.darkvelton69.inputproject.entity.Passport;
import by.darkvelton69.inputproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassportRepository extends JpaRepository<Passport, Long> {
    Long countByUser_Id(Long userId);

    Passport findByUserEmail(String userEmail);
}
