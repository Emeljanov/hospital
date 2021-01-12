package by.emel.anton.model.beans.therapy;

import by.emel.anton.constants.Constants;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "therapies")
public abstract class Therapy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_therapy")
    private int id;
    @Column(unique = true, nullable = false)
    private String description;
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    @Column(name = "id_patient", nullable = false)

    private int idPatient;

    public Therapy() {
    }

    public Therapy(int id , String description, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public int getIdPatient() {
        return idPatient;
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

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    @Override
    public String toString() {
        return id + Constants.SEPARATOR + description + Constants.SEPARATOR + startDate + Constants.SEPARATOR + endDate + Constants.SEPARATOR + idPatient;
    }
}
