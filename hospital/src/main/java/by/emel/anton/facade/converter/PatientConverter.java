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
        return ResponsePatientDTO.builder()
                .id(from.getId())
                .name(from.getName())
                .login(from.getLogin())
                .doctorId(from.getDoctor().getId())
                .therapyIds(from.getTherapies().stream().map(Therapy::getId).collect(Collectors.toList()))
                .build();
    }
}
