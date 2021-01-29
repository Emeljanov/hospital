package by.emel.anton.facade.patient;

import by.emel.anton.facade.converter.Converter;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDaoUncheckedException;
import by.emel.anton.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class PatientFacadeImpl implements PatientFacade {

    @Autowired
    @Qualifier("SpringDataService")
    private UserService userService;
    @Autowired
    private Converter<Patient, ResponsePatientDTO> converter;

    @Override
    public ResponsePatientDTO getPatientById(int id) throws UserDaoUncheckedException {

        return userService
                .getPatientById(id)
                .map(converter::convert)
                .orElseThrow(() -> new UserDaoUncheckedException("Can't find patient with id : " + id));
    }

    @Override
    public ResponsePatientDTO getPatientByLogPass(String login, String password) throws UserDaoUncheckedException {

        return userService
                .getPatient(login, password)
                .map(converter::convert)
                .orElseThrow(() -> new UserDaoUncheckedException("Login or password are incorrect"));
    }
}
