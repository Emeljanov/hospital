package by.emel.anton.model.dao.implementation.filedao;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.interfaces.PatientDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository("FilePatientDAO")
public class FilePatientDAO implements PatientDAO {



    @Override
    public Optional<Patient> getPatient(String login, String password) throws UserDAOException {

        try {
            List<String> fileData = Files.readAllLines(Paths.get(Constants.FILE_PATH_USERS));

            return fileData
                    .stream()
                    .filter(s -> FileService.isLoginPasswordCorrect(s, login, password))
                    .findFirst()
                    .map(this::createPatient);
        } catch (IOException e) {
            throw new UserDAOException("ERROR getPatient from file");
        }

    }

    @Override
    public Optional<Patient> getPatientById(int id) throws UserDAOException {

        try {
            List<String> fileData = Files.readAllLines(Paths.get(Constants.FILE_PATH_USERS));

            return fileData
                    .stream()
                    .filter(s -> FileService.findLineById(s, id))
                    .findFirst()
                    .map(this::createPatient);
        } catch (IOException e) {
            throw new UserDAOException("ERROR get Patient by ID from FILE");
        }

    }

    private Patient createPatient(String lineData) {

        String[] userData = lineData.split(Constants.SEPARATOR);
        int id = Integer.parseInt(userData[0]);
        String login = userData[1];
        String password = userData[2];
        String name = userData[3];
        LocalDate birthday = LocalDate.parse(userData[4]);
        Patient patient = new Patient();
        patient.setId(id);
        patient.setLogin(login);
        patient.setPassword(password);
        patient.setName(name);
        patient.setBirthday(birthday);

        try {
            FileService.addDoctorToPatient(patient);
        } catch (UserDAOException e) {
            e.printStackTrace();
        }
        FileService.addTherapiesToPatient(patient);


        return patient;

    }

}
