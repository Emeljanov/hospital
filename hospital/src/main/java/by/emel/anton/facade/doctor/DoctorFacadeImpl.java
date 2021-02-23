package by.emel.anton.facade.doctor;

import by.emel.anton.facade.converter.Converter;
import by.emel.anton.facade.therapy.RequestTherapyDTO;
import by.emel.anton.model.dao.exceptions.UserDaoException;
import by.emel.anton.model.entity.therapy.Therapy;
import by.emel.anton.model.entity.users.doctors.Doctor;
import by.emel.anton.model.entity.users.patients.Patient;
import by.emel.anton.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class DoctorFacadeImpl implements DoctorFacade {

    private UserService userService;
    private Converter<Doctor, ResponseDoctorDTO> converter;

    @Autowired
    public DoctorFacadeImpl(@Qualifier("SpringDataService") UserService userService, Converter<Doctor, ResponseDoctorDTO> converter) {
        this.userService = userService;
        this.converter = converter;
    }

    @Override
    public void setPatientToDoctor(int patientId) {


        org.springframework.security.core.userdetails.User usr = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = usr.getUsername();

        Doctor doctor = userService
                .getDoctorByLogin(login)
                .orElseThrow(() -> new UserDaoException("didn't find doctor with id : "));

        userService.addPatientToDoctor(doctor, patientId);

    }

    @Override
    public void setTherapyToPatient(RequestTherapyDTO requestTherapyDTO) {

        org.springframework.security.core.userdetails.User usr = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = usr.getUsername();
        int patientId = requestTherapyDTO.getPatientId();
        Doctor doctor = userService
                .getDoctorByLogin(login)
                .orElseThrow(() -> new UserDaoException("didn't find doctor with login : " + login));
        Patient patient = userService
                .getPatientById(patientId)
                .orElseThrow(() -> new UserDaoException("didn't find doctor with id : " + patientId));

        Therapy therapy = new Therapy();
        therapy.setPatient(patient);
        therapy.setStartDate(requestTherapyDTO.getStartDate());
        therapy.setEndDate(requestTherapyDTO.getEndDate());
        therapy.setDescription(requestTherapyDTO.getDescription());

        userService.saveTherapy(therapy);
    }

    @Override
    public ResponseDoctorDTO getDoctorByLogin(String login) {
        ResponseDoctorDTO responseDoctorDTO = userService
                .getDoctorByLogin(login)
                .map(converter::convert)
                .orElseThrow(() -> new UserDaoException("Login or password are incorrect"));

        return responseDoctorDTO;
    }

}
