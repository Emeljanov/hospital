package by.emel.anton.model.dao.implementation.filedoa;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.beans.users.patients.OrdinaryPatient;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.interfaces.PatientDAO;
import by.emel.anton.service.StringToList;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository("PatientFromFile")
public class FilePatientDAO implements PatientDAO {

    @Override
    public Optional<Patient> getPatient(String login, String password) throws UserDAOException {

        try {
            List<String> fileData = Files.readAllLines(Paths.get(Constants.FILE_PATH_PATIENTS));

            return fileData
                    .stream()
                    .filter(s -> FileService.isLoginPasswordCorrect(s,login,password))
                    .findFirst()
                    .map(this::createPatientFromLine);
        }
        catch (IOException e) {
            throw new UserDAOException("ERROR getPatient from file");
        }


    }

    @Override
    public Optional<Patient> getPatientById(int id) throws UserDAOException {

        try {
            List<String> fileData = Files.readAllLines(Paths.get(Constants.FILE_PATH_PATIENTS));

            return fileData
                    .stream()
                    .filter(s -> FileService.findLineById(s,id))
                    .findFirst()
                    .map(this::createPatientFromLine);
        }
        catch (IOException e) {
            throw new UserDAOException("ERROR get Patient by ID from FILE");
        }
    }

    private Patient createPatientFromLine(String lineData) {

        String[] userData = lineData.split(Constants.SEPARATOR);
        int id = Integer.parseInt(userData[0]);
        String login = userData[1];
        String password = userData[2];
        String name = userData[4];
        LocalDate birthday = LocalDate.parse(userData[5]);
        int doctorId = Integer.parseInt(userData[6]);
        List<Integer> therapiesId = StringToList.toIntegerList(userData[7]);
        Patient patient = new OrdinaryPatient(id,login,password,name,birthday,doctorId);
        patient.setTherapies(therapiesId);
        return patient;

    }

}
