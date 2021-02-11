package by.emel.anton.facade.therapy;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class RequestTherapyDTO {

    @NotNull(message = "field description = null")
    private String description;
    @NotNull(message = "field startDate = null")
    private LocalDate startDate;
    @NotNull(message = "field endDate = null")
    private LocalDate endDate;
    @NotNull(message = "field patientId = null")
    private int patientId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
}
