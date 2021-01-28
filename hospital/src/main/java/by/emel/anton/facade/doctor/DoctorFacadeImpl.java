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
import java.util.Optional;

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

        ResponseDoctorDTO responseDoctorDTO = userService.getDoctor(login, password).map(converter::convert).orElseThrow(IllegalArgumentException::new);
        httpSession.setAttribute("doctorId", responseDoctorDTO.getId());
        return responseDoctorDTO;

    }

    @Override
    public void setPatientToDoctor(int doctorId, int patientId) throws UserDAOException {
        Optional<Doctor> optionalDoctor = userService.getDoctorById(doctorId);
        if (!optionalDoctor.isPresent()) throw new UserDAOException("no found doctor");
        userService.addPatientToDoctor(optionalDoctor.get(), patientId);
    }

    @Override
    public void setTherapyToPatient(int doctorId, RequestTherapyDTO requestTherapyDTO) throws UserDAOException, TherapyDAOException {

        Optional<Doctor> optionalDoctor = userService.getDoctorById(doctorId);
        Optional<Patient> optionalPatient = userService.getPatientById(requestTherapyDTO.getPatientId());
        if (!optionalDoctor.isPresent()) throw new UserDAOException("no doctor found");
        if(!optionalPatient.isPresent()) throw new UserDAOException("no patient found");
        Doctor doctor = optionalDoctor.get();
        Patient patient = optionalPatient.get();

        Therapy therapy = new Therapy();
        therapy.setPatient(patient);
        therapy.setStartDate(requestTherapyDTO.getStartDate());
        therapy.setEndDate(requestTherapyDTO.getEndDate());
        therapy.setDescription(requestTherapyDTO.getDescription());
        userService.saveTherapy(therapy);
    }

}
