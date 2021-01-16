package by.emel.anton.model.beans.therapy;

import by.emel.anton.constants.Constants;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import java.time.LocalDate;


//@MappedSuperclass
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Therapy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id_therapy")
    private int id;
    @Column(nullable = false)
    private String description;
    @Column(name = "start_date", nullable = false, columnDefinition = "TIME WITH TIME ZONE")
    private LocalDate startDate;
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    @Column(name = "patient_id", nullable = false)
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
