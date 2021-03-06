package by.emel.anton.model.entity.therapy;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.entity.users.patients.Patient;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

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

    public Therapy() {
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return String.join(Constants.SEPARATOR, String.valueOf(id), description, startDate.toString(), endDate.toString(), String.valueOf(patient.getId()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Therapy therapy = (Therapy) o;
        return id == therapy.id &&
                description.equals(therapy.description) &&
                startDate.equals(therapy.startDate) &&
                endDate.equals(therapy.endDate) &&
                patient.equals(therapy.patient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, startDate, endDate, patient);
    }
}
