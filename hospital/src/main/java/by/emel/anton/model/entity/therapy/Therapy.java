package by.emel.anton.model.entity.therapy;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.entity.users.patients.Patient;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class Therapy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String description;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Override
    public String toString() {
        return String.join(Constants.SEPARATOR, String.valueOf(id), description, startDate.toString(), endDate.toString(), String.valueOf(patient.getId()));
    }
}
