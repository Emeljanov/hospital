package by.emel.anton.model.dao.implementation.filedao;

import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.interfaces.PatientDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("FilePatientDAO")
public class FilePatientDAO implements PatientDAO {

    FileUserDAO fileUserDAO;

    @Autowired
    public FilePatientDAO(FileUserDAO fileUserDAO) {
        this.fileUserDAO = fileUserDAO;
    }

    @Override
    public Optional<Patient> getPatient(String login, String password) throws UserDAOException {
        return fileUserDAO.getPatientFromFile(login, password);
    }

    @Override
    public Optional<Patient> getPatientById(int id) throws UserDAOException {

        return fileUserDAO.getPatientFromFileByID(id);
    }
}
