package by.emel.anton.model.dao.implementation.filedao;

import by.emel.anton.model.entity.users.doctors.Doctor;
import by.emel.anton.model.dao.interfaces.DoctorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("FileDoctorDAO")
public class FileDoctorDAO implements DoctorDAO {

    private FileUserDAO fileUserDAO;

    @Autowired
    public FileDoctorDAO(FileUserDAO fileUserDAO) {
        this.fileUserDAO = fileUserDAO;
    }

    @Override
    public Optional<Doctor> getDoctor(String login, String password) {
        return fileUserDAO.getDoctorFromFile(login, password);
    }

    @Override
    public Optional<Doctor> getDoctorById(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<Doctor> getDoctorByLogin(String login) {
        return Optional.empty();
    }
}
