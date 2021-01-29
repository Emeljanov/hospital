package by.emel.anton.facade.doctor;

import by.emel.anton.facade.converter.Converter;
import by.emel.anton.facade.therapy.RequestTherapyDTO;
import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.TherapyDAOException;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class DoctorFacadeImpl implements DoctorFacade {

    @Autowired
    @Qualifier("SpringDataService")
    private UserService userService;

    @Autowired
    private Converter<Doctor, ResponseDoctorDTO> converter;

    @Autowired
    HttpSession httpSession;

    @Override
    public ResponseDoctorDTO getDoctorByLoginPassword(String login, String password) throws UserDAOException {

        ResponseDoctorDTO responseDoctorDTO = userService
                .getDoctor(login, password)
                .map(converter::convert)
                .orElseThrow(() -> new UserDAOException("Login or password are incorrect"));
        httpSession.setAttribute("doctorId", responseDoctorDTO.getId());

        return responseDoctorDTO;

    }

    @Override
    public void setPatientToDoctor(int doctorId, int patientId) throws UserDAOException {

        Doctor doctor = userService
                .getDoctorById(doctorId)
                .orElseThrow(() -> new UserDAOException("didn't find doctor with id : " + doctorId));

        userService.addPatientToDoctor(doctor, patientId);

    }

    @Override
    public void setTherapyToPatient(int doctorId, RequestTherapyDTO requestTherapyDTO) throws UserDAOException, TherapyDAOException {

        int patientId = requestTherapyDTO.getPatientId();
        Doctor doctor = userService
                .getDoctorById(doctorId)
                .orElseThrow(() -> new UserDAOException("didn't find doctor with id : " + doctorId));
        Patient patient = userService
                .getPatientById(patientId)
                .orElseThrow(() -> new UserDAOException("didn't find doctor with id : " + patientId));

        Therapy therapy = new Therapy();
        therapy.setPatient(patient);
        therapy.setStartDate(requestTherapyDTO.getStartDate());
        therapy.setEndDate(requestTherapyDTO.getEndDate());
        therapy.setDescription(requestTherapyDTO.getDescription());

        userService.saveTherapy(therapy);
    }

}
