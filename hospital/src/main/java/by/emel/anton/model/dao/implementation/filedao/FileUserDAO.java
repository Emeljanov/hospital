package by.emel.anton.model.dao.implementation.filedao;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.beans.users.UserType;
import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.interfaces.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Repository("FileUserDAO")
public class FileUserDAO implements UserDAO {

    private final Path userPath = Paths.get(Constants.FILE_PATH_USERS);
    private final Path patientPath = Paths.get(Constants.FILE_PATH_PATIENTS);
    private final Path doctorPath = Paths.get(Constants.FILE_PATH_DOCTORS);

    private final BiFunction<String, String, Boolean> bifunctionfIsLoginExist = (line, login) -> login.equals(line.split(Constants.SEPARATOR)[1]);
    private final BiFunction<String, String, Boolean> bifunctionIsIdExist = (line, idString) -> idString.equals(line.split(Constants.SEPARATOR)[0]);


    @Override
    public boolean isLoginExist(String login) throws UserDAOException {

        try {

            List<String> fileDataUsers = Files.readAllLines(userPath);
            return fileDataUsers.stream().anyMatch(line -> bifunctionfIsLoginExist.apply(line, login));

        } catch (IOException e) {
            throw new UserDAOException("ERROR in method isLoginExist in file");
        }
    }

    @Override
    public void saveUser(User user) throws UserDAOException {

        try {

            String login = user.getLogin();
            if (isLoginExist(login)) {
                return;
            }
            String password = user.getPassword();
            String name = user.getName();
            String birthday = user.getBirthday().toString();
            String idStr = null;
            idStr = String.valueOf(FileService.getNextLineId(userPath));

            UserType userType = user.getUserType();

            String lineUserForWrite = String.join(Constants.SEPARATOR, idStr, login, password, name, birthday, userType.toString());
            writeInFile(lineUserForWrite, userPath);

            if (UserType.DOCTOR == userType) {

                writeInFile(idStr, doctorPath);

            } else if (UserType.PATIENT == userType) {

                Patient patient = (Patient) user;
                Optional<Doctor> doctor = Optional.ofNullable(patient.getDoctor());
                String doctorIdLine = "0";

                if (doctor.isPresent()) {
                    doctorIdLine = String.valueOf(doctor.get().getId());
                }
                String linePatientToWrite = String.join(Constants.SEPARATOR, idStr, doctorIdLine);
                writeInFile(linePatientToWrite, patientPath);
            }

        } catch (IOException e) {
            throw new UserDAOException("ERROR with user file");
        }
    }

    @Override
    public void updateUser(User user) throws UserDAOException {

        String login = user.getLogin();
        UserType userType = user.getUserType();
        String userId = String.valueOf(user.getId());

        filterAndDeleteDataFromFile(userPath, login, bifunctionfIsLoginExist);

        if (userType == UserType.DOCTOR) {
            filterAndDeleteDataFromFile(doctorPath, userId, bifunctionIsIdExist);
        } else if (userType == UserType.PATIENT) {
            filterAndDeleteDataFromFile(patientPath, userId, bifunctionIsIdExist);
        }
        saveUser(user);
    }

    private void writeInFile(String writeLine, Path filePath) throws UserDAOException {
        List<String> lines = Collections.singletonList(writeLine);
        try {
            Files.write(filePath, lines, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new UserDAOException("ERROR : can't save data in file");
        }
    }

    private void filterAndDeleteDataFromFile(Path filepath, String compareBy, BiFunction<String, String, Boolean> bif) throws UserDAOException {
        try {
            List<String> dataFromFile = Files.readAllLines(filepath);
            List<String> dataToWrite = dataFromFile
                    .stream()
                    .filter(data -> !bif.apply(data, compareBy))
                    .collect(Collectors.toList());
            Files.write(filepath, dataToWrite);
        } catch (IOException e) {
            throw new UserDAOException("ERROR delete data from file");
        }
    }
}
