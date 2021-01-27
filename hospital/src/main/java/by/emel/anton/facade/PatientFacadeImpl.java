package by.emel.anton.facade;

import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDAOException;
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
    public ResponsePatientDTO getPatientById(String id) {

        try {
           return userService.getPatientById(Integer.parseInt(id)).map(converter::convert).orElseThrow(IllegalArgumentException::new);
//            return converter.convert(patient);

        } catch (UserDAOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponsePatientDTO getPatientByLogPass(String login, String password) {

        try {
           return userService.getPatient(login, password).map(converter::convert).orElse(null);
//            return converter.convert(patient);
        } catch (UserDAOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
