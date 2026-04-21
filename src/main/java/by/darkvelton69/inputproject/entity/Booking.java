package by.darkvelton69.inputproject.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookings_id_gen")
    @SequenceGenerator(name = "bookings_id_gen", sequenceName = "bookings_seq")
    private Long id;

    @Column(name = "appointment_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate appointmentDate;

    @Column(name = "appointment_time", nullable = false)
    @JsonFormat(pattern = "HH:mm")
    private LocalTime appointmentTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Condition condition;

}
