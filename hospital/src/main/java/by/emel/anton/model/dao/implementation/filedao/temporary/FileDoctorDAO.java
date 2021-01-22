package by.emel.anton.model.dao.implementation.filedao.temporary;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.implementation.filedao.FileService;
import by.emel.anton.model.dao.interfaces.DoctorDAO;
import by.emel.anton.service.StringToList;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository("DoctorFromFile")
public class FileDoctorDAO implements DoctorDAO {

    @Override
    public Optional<Doctor> getDoctor(String login, String password) throws UserDAOException {

        try {
            List<String> fileData = Files.readAllLines(Paths.get(Constants.FILE_PATH_DOCTORS));

            return fileData
                    .stream()
                    .filter(s -> FileService.isLoginPasswordCorrect(s,login,password))
                    .map(this::createDoctorFromLine)
                    .findFirst();
        }
        catch (IOException e) {
            throw new UserDAOException("ERROR getDoctor from file");
        }

    }

    private Doctor createDoctorFromLine(String lineData) {

        String[] userData = lineData.split(Constants.SEPARATOR);
        int id = Integer.parseInt(userData[0]);
        String login = userData[1];
        String password = userData[2];
        LocalDate birthday = LocalDate.parse(userData[5]);
        String name = userData[4];
        List<Integer> patients = StringToList.toIntegerList(userData[6]);
        Doctor doctor = new Doctor(id,login,password,name,birthday);
        patients.forEach(doctor::setPatientId);
        return doctor;

    }
}
