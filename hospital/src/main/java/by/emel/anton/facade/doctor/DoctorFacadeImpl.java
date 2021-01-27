package by.emel.anton.facade.doctor;

import by.emel.anton.facade.converter.Converter;
import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class DoctorFacadeImpl implements DoctorFacade {
    @Autowired
    @Qualifier("SpringDataService")
    private UserService userService;
    @Autowired
    private Converter<Doctor, ResponseDoctorDTO> converter;


    @Override
    public ResponseDoctorDTO getDoctorByLoginPassword(String login, String password) {

        try {
            return userService.getDoctor(login,password).map(converter::convert).orElseThrow(IllegalArgumentException::new);
        } catch (UserDAOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
