package by.darkvelton69.inputproject.repository;

import by.darkvelton69.inputproject.entity.Client;
import by.darkvelton69.inputproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByUser_Email(String userEmail);

    @Query("SELECT c FROM Client c JOIN FETCH c.user WHERE c.telegramChatId = :chatId")
    Optional<Client> findByTelegramChatId(@Param("chatId") Long chatId);

    Optional<Client> findByPhone(String phone);
}
