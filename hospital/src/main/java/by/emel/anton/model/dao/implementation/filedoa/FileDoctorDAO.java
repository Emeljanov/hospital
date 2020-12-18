package by.emel.anton.model.dao.implementation.filedoa;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.beans.users.doctors.GeneralDoctor;
import by.emel.anton.model.dao.interfaces.DoctorDAO;
import by.emel.anton.service.StringToList;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class FileDoctorDAO implements DoctorDAO {

    @Override
    public Optional<Doctor> getDoctor(String login, String password) throws IOException {

        List<String> fileData = Files.readAllLines(Paths.get(Constants.FILE_PATH_DOCTORS));

        return fileData
                .stream()
                .filter(s -> FileService.isLoginPasswordCorrect(s,login,password))
                .map(this::createDoctorFromLine)
                .findFirst();

    }

    private Doctor createDoctorFromLine(String lineData) {

        String[] userData = lineData.split(Constants.SEPARATOR);
        int id = Integer.parseInt(userData[0]);
        String login = userData[1];
        String password = userData[2];
        LocalDate birthday = LocalDate.parse(userData[5]);
        String name = userData[4];
        List<Integer> patients = StringToList.toIntegerList(userData[6]);
        Doctor Doctor = new GeneralDoctor(id,login,password,name,birthday);
        patients.forEach(Doctor::setPatientId);
        return Doctor;

    }
}
