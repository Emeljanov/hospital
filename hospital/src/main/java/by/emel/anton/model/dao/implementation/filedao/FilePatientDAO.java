package by.emel.anton.model.dao.implementation.filedao;

import by.emel.anton.model.entity.users.patients.Patient;
import by.emel.anton.model.dao.interfaces.PatientDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("FilePatientDAO")
public class FilePatientDAO implements PatientDAO {

    private FileUserDAO fileUserDAO;

    @Autowired
    public FilePatientDAO(FileUserDAO fileUserDAO) {
        this.fileUserDAO = fileUserDAO;
    }

    @Override
    public Optional<Patient> getPatient(String login, String password) {
        return fileUserDAO.getPatientFromFile(login, password);
    }

    @Override
    public Optional<Patient> getPatientById(int id) {

        return fileUserDAO.getPatientFromFileByID(id);
    }

    @Override
    public Optional<Patient> getPatientByLogin(String login) {
        return Optional.empty();
    }
}
