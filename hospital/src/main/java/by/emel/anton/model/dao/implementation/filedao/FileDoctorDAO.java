package by.emel.anton.model.dao.implementation.filedao;

import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.interfaces.DoctorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("FileDoctorDAO")
public class FileDoctorDAO implements DoctorDAO {

    FileUserDAO fileUserDAO;

    @Autowired
    public FileDoctorDAO(FileUserDAO fileUserDAO) {
        this.fileUserDAO = fileUserDAO;
    }

    @Override
    public Optional<Doctor> getDoctor(String login, String password) throws UserDAOException {
        return fileUserDAO.getDoctorFromFile(login, password);
    }
}
