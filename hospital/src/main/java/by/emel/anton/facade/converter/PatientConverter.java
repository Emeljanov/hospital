package by.emel.anton.facade.converter;

import by.emel.anton.facade.patient.ResponsePatientDTO;
import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.beans.users.patients.Patient;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PatientConverter implements Converter<Patient, ResponsePatientDTO> {
    @Override
    public ResponsePatientDTO convert(Patient from) {
        ResponsePatientDTO responsePatientDTO = new ResponsePatientDTO();
        responsePatientDTO.setId(from.getId());
        responsePatientDTO.setName(from.getName());
        responsePatientDTO.setLogin(from.getLogin());
        responsePatientDTO.setTherapнIds(from.getTherapies().stream().map(Therapy::getId).collect(Collectors.toList()));
        return responsePatientDTO;
    }
}
