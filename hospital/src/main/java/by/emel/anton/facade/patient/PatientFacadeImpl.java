package by.emel.anton.facade.patient;

import by.emel.anton.facade.converter.Converter;
import by.emel.anton.model.entity.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDaoException;
import by.emel.anton.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class PatientFacadeImpl implements PatientFacade {

    private UserService userService;
    private Converter<Patient, ResponsePatientDTO> converter;
    private HttpSession httpSession;

    @Autowired
    public PatientFacadeImpl(@Qualifier("SpringDataService") UserService userService, Converter<Patient, ResponsePatientDTO> converter, HttpSession httpSession) {
        this.userService = userService;
        this.converter = converter;
        this.httpSession = httpSession;
    }

    @Override
    public ResponsePatientDTO getPatientById(int id) {

        return userService
                .getPatientById(id)
                .map(converter::convert)
                .orElseThrow(() -> new UserDaoException("Can't find patient with id : " + id));
    }

    @Override
    public ResponsePatientDTO getPatientByLogPass(String login, String password) {
        ResponsePatientDTO responsePatientDTO = userService
                .getPatient(login, password)
                .map(converter::convert)
                .orElseThrow(() -> new UserDaoException("Login or password are incorrect"));

        int patientId = responsePatientDTO.getId();
        httpSession.setAttribute("patientId", patientId);

        return responsePatientDTO;
    }
}
