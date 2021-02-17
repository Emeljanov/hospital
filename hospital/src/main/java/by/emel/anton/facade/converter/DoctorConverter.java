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
        ResponseDoctorDTO responseDoctorDTO = new ResponseDoctorDTO();
        responseDoctorDTO.setId(from.getId());
        responseDoctorDTO.setLogin(from.getLogin());
        responseDoctorDTO.setName(from.getName());
        responseDoctorDTO.setPatientIds(from.getPatients().stream().map(User::getId).collect(Collectors.toList()));

        return responseDoctorDTO;
    }
}
