package by.emel.anton.facade.therapy;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ResponseTherapyDTO {

    private int therapyId;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private int patientId;

}
