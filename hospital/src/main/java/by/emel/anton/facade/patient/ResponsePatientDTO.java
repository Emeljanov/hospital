package by.emel.anton.facade.patient;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class ResponsePatientDTO {

    private int id;
    private String login;
    private String name;
    private List<Integer> therapyIds;
    private int doctorId;
}
