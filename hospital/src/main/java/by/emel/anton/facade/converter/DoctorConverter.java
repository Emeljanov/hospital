package by.emel.anton.facade.converter;

import by.emel.anton.facade.doctor.ResponseDoctorDTO;
import by.emel.anton.model.entity.users.User;
import by.emel.anton.model.entity.users.doctors.Doctor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DoctorConverter implements Converter<Doctor, ResponseDoctorDTO> {
    @Override
    public ResponseDoctorDTO convert(Doctor from) {
        return ResponseDoctorDTO.builder()
                .id(from.getId())
                .login(from.getLogin())
                .name(from.getName())
                .patientIds(from.getPatients().stream().map(User::getId).collect(Collectors.toList()))
                .build();
    }
}
