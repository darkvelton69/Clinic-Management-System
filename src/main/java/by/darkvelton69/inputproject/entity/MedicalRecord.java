package by.darkvelton69.inputproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "medical_records")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medical_records_id_gen")
    @SequenceGenerator(name = "medical_records_id_gen", sequenceName = "medical_records_seq")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_type", nullable = false)
    private BloodType bloodType;

    @Enumerated(EnumType.STRING)
    @Column(name = "rh_factor", nullable = false)
    private RhFactor rhFactor;

    @Column(name = "allergies", nullable = false)
    private String allergies;

    @Column(name = "chronic_diseases", nullable = false)
    private String chronicDiseases;

    @Column(name = "family_history", nullable = false)
    private String familyHistory;

    @Column(name = "insurance_number", nullable = false)
    private String insuranceNumber;




    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false, unique = true)
    private Client client;



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MedicalRecord that = (MedicalRecord) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
