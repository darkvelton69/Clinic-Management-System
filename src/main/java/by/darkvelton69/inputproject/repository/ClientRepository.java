package by.darkvelton69.inputproject.repository;

import by.darkvelton69.inputproject.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByUser_Email(String userEmail);
}
