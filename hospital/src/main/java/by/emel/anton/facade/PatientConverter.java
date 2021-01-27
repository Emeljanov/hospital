package by.emel.anton.facade;

import by.emel.anton.model.beans.users.patients.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientConverter implements Converter<Patient, ResponsePatientDTO> {
    @Override
    public ResponsePatientDTO convert(Patient from) {
        ResponsePatientDTO responsePatientDTO = new ResponsePatientDTO();
        responsePatientDTO.setId(from.getId());
        responsePatientDTO.setName(from.getName());
        return responsePatientDTO;
    }
}
