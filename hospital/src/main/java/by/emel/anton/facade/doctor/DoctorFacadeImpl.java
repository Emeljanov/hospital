package by.emel.anton.facade.doctor;

import by.emel.anton.config.service.SecurityService;
import by.emel.anton.facade.converter.Converter;
import by.emel.anton.facade.therapy.RequestTherapyDTO;
import by.emel.anton.model.entity.therapy.Therapy;
import by.emel.anton.model.entity.users.doctors.Doctor;
import by.emel.anton.model.entity.users.patients.Patient;
import by.emel.anton.service.UserService;
import by.emel.anton.service.exception.UserServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class DoctorFacadeImpl implements DoctorFacade {

    private UserService userService;
    private Converter<Doctor, ResponseDoctorDTO> converter;
    private SecurityService securityService;

    @Autowired
    public DoctorFacadeImpl(@Qualifier("SpringDataService") UserService userService,
                            Converter<Doctor, ResponseDoctorDTO> converter,
                            SecurityService securityService) {
        this.userService = userService;
        this.converter = converter;
        this.securityService = securityService;
    }

    @Override
    public void setPatientToDoctor(int patientId) {

        String login = securityService.getLoginFromUserDetails();

        Doctor doctor = userService
                .getDoctorByLogin(login)
                .orElseThrow(() -> new UserServiceException("didn't find doctor with id : "));

        userService.addPatientToDoctor(doctor, patientId);

    }

    @Override
    public void setTherapyToPatient(RequestTherapyDTO requestTherapyDTO) {

        int patientId = requestTherapyDTO.getPatientId();

        Patient patient = userService
                .getPatientById(patientId)
                .orElseThrow(() -> new UserServiceException("didn't find doctor with id : " + patientId));

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
                .orElseThrow(() -> new UserServiceException("Login or password are incorrect"));

        return responseDoctorDTO;
    }

}
