package by.emel.anton.model.dao.implementation.filedao;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.interfaces.DoctorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository("FileDoctorDAO")
public class FileDoctorDAO implements DoctorDAO {

    @Override
    public Optional<Doctor> getDoctor(String login, String password) throws UserDAOException {
        try {
            List<String> fileData = Files.readAllLines(Paths.get(Constants.FILE_PATH_USERS));

            return fileData
                    .stream()
                    .filter(s -> FileService.isLoginPasswordCorrect(s, login, password))
                    .findFirst()
                    .map(this::createDoctor);
        } catch (IOException e) {
            throw new UserDAOException("ERROR getDoctor from file");
        }

    }

    private Doctor createDoctor(String lineData) {

        String[] userData = lineData.split(Constants.SEPARATOR);
        int id = Integer.parseInt(userData[0]);
        String login = userData[1];
        String password = userData[2];
        LocalDate birthday = LocalDate.parse(userData[4]);
        String name = userData[3];

        Doctor doctor = new Doctor();
        doctor.setId(id);
        doctor.setLogin(login);
        doctor.setPassword(password);
        doctor.setName(name);
        doctor.setBirthday(birthday);

        try {
            FileService.addPatientsToDoctor(doctor);
        } catch (UserDAOException e) {
            e.printStackTrace();
        }

        return doctor;

    }
    public Optional<Doctor> getDoctorById(int id) throws UserDAOException {
        try {
            List<String> fileData = Files.readAllLines(Paths.get(Constants.FILE_PATH_USERS));

            return fileData
                    .stream()
                    .filter(s -> Integer.parseInt(s.split(Constants.SEPARATOR)[0])==id)
                    .findFirst()
                    .map(this::createDoctor);
        } catch (IOException e) {
            throw new UserDAOException("ERROR getDoctor from file");
        }

    }


}
