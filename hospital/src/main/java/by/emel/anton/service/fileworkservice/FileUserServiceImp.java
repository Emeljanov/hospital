package by.emel.anton.service.fileworkservice;

import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.interfaces.DoctorDAO;
import by.emel.anton.model.dao.interfaces.PatientDAO;
import by.emel.anton.model.dao.interfaces.TherapyDAO;
import by.emel.anton.model.dao.interfaces.UserDAO;
import by.emel.anton.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service("FileService")
public class FileUserServiceImp extends UserServiceImp {

    public FileUserServiceImp(
            @Qualifier("FileUserDAO") UserDAO userDAO,
            @Qualifier("FilePatientDAO") PatientDAO patientDAO,
            @Qualifier("FileDoctorDAO") DoctorDAO doctorDAO,
            @Qualifier("FileTherapyDAO") TherapyDAO therapyDAO) {
        super(userDAO, patientDAO, doctorDAO, therapyDAO);
    }
//id dopis
 /*   @Override
    public void createUser(User user, String login, String password, String name, LocalDate birthday, boolean isSave) throws UserDAOException {
        super.createUser(user, login, password, name, birthday, isSave);
    }*/
}
