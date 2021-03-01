package by.emel.anton.facade.therapy;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class RequestTherapyDTO {

    @NotNull(message = "field description = null")
    private String description;
    @NotNull(message = "field startDate = null")
    private LocalDate startDate;
    @NotNull(message = "field endDate = null")
    private LocalDate endDate;
    @NotNull(message = "field patientId = null")
    private int patientId;
}
