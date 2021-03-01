package by.emel.anton.facade.doctor;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResponseDoctorDTO {
    private int id;
    private String login;
    private String name;
    private List<Integer> patientIds;
}
