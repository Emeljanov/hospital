package by.emel.anton.facade.therapy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Builder
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
