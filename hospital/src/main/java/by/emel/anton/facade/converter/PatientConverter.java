package by.emel.anton.facade.converter;

import by.emel.anton.facade.patient.ResponsePatientDTO;
import by.emel.anton.model.entity.therapy.Therapy;
import by.emel.anton.model.entity.users.patients.Patient;
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
        responsePatientDTO.setTherapyIds(from.getTherapies().stream().map(Therapy::getId).collect(Collectors.toList()));

        return responsePatientDTO;
    }
}
