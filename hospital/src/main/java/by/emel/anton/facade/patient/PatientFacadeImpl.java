package by.emel.anton.facade.patient;

import by.emel.anton.facade.converter.Converter;
import by.emel.anton.model.dao.exceptions.UserDaoException;
import by.emel.anton.model.entity.users.patients.Patient;
import by.emel.anton.service.UserService;
import by.emel.anton.service.exception.UserServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class PatientFacadeImpl implements PatientFacade {

    private UserService userService;
    private Converter<Patient, ResponsePatientDTO> converter;

    @Autowired
    public PatientFacadeImpl(@Qualifier("SpringDataService") UserService userService, Converter<Patient, ResponsePatientDTO> converter) {
        this.userService = userService;
        this.converter = converter;
    }

    @Override
    public ResponsePatientDTO getPatientById(int id) throws UserServiceException {

        return userService
                .getPatientById(id)
                .map(converter::convert)
                .orElseThrow(() -> new UserDaoException("Can't find patient with id : " + id));
    }

    @Override
    public ResponsePatientDTO getPatientByLogin(String login) {

        return userService.getPatientByLogin(login).map(converter::convert)
                .orElseThrow(() -> new UserDaoException("Can't find patient with login : " + login));
    }
}
