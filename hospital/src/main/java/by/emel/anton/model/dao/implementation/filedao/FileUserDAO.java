package by.emel.anton.model.dao.implementation.filedao;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.entity.users.User;
import by.emel.anton.model.entity.users.UserType;
import by.emel.anton.model.entity.users.doctors.Doctor;
import by.emel.anton.model.entity.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDaoException;
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

    private final BiFunction<String, String, Boolean> biFunctionIsLoginExist = (line, login) -> login.equals(line.split(Constants.SEPARATOR)[1]);
    private final BiFunction<String, String, Boolean> biFunctionIsIdExist = (line, idString) -> idString.equals(line.split(Constants.SEPARATOR)[0]);

    private FileServiceDAO fileServiceDAO;

    @Autowired
    public FileUserDAO(FileServiceDAO fileServiceDAO) {
        this.fileServiceDAO = fileServiceDAO;
    }

    @Override
    public boolean isLoginExist(String login) {

        try {
            List<String> fileDataUsers = Files.readAllLines(userPath);
            return fileDataUsers.stream().anyMatch(line -> biFunctionIsLoginExist.apply(line, login));

        } catch (IOException e) {
            throw new UserDaoException("ERROR in method isLoginExist in file");
        }
    }

    @Override
    public void saveUser(User user) {

        String login = user.getLogin();
        if (isLoginExist(login)) {
            throw new UserDaoException("ERROR Login is exist");
        }
        String password = user.getPassword();
        String name = user.getName();
        String birthday = user.getBirthday().toString();
        String idStr = String.valueOf(fileServiceDAO.getNextLineId(userPath));

        UserType userType = user.getUserType();

        String lineUserForWrite = String.join(Constants.SEPARATOR, idStr, login, password, name, birthday, userType.toString());
        writeInFile(lineUserForWrite, userPath);

        if (UserType.DOCTOR == userType) {

            writeInFile(idStr, doctorPath);

        } else if (UserType.PATIENT == userType) {

            Patient patient = (Patient) user;
            Optional<Doctor> doctorPlug = Optional.ofNullable(patient.getDoctor());
            String doctorIdLine = "0";

            if (doctorPlug.isPresent()) {
                doctorIdLine = String.valueOf(doctorPlug.get().getId());
            }
            String linePatientToWrite = String.join(Constants.SEPARATOR, idStr, doctorIdLine);
            writeInFile(linePatientToWrite, patientPath);
        }
    }

    @Override
    public Optional<User> getSimpleUserByLogin(String login) {
        return null;
    }

    @Override
    public void updateUser(User user) {

        String login = user.getLogin();
        UserType userType = user.getUserType();
        String userId = String.valueOf(user.getId());

        filterAndDeleteDataFromFile(userPath, login, biFunctionIsLoginExist);

        if (userType == UserType.DOCTOR) {
            filterAndDeleteDataFromFile(doctorPath, userId, biFunctionIsIdExist);
        } else if (userType == UserType.PATIENT) {
            filterAndDeleteDataFromFile(patientPath, userId, biFunctionIsIdExist);
        }
        saveUser(user);
    }

    private void writeInFile(String writeLine, Path filePath) {
        List<String> lines = Collections.singletonList(writeLine);
        try {
            Files.write(filePath, lines, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new UserDaoException("ERROR : can't save data in file");
        }
    }

    private void filterAndDeleteDataFromFile(Path filepath, String compareBy, BiFunction<String, String, Boolean> bif) {
        try {
            List<String> dataFromFile = Files.readAllLines(filepath);
            List<String> dataToWrite = dataFromFile
                    .stream()
                    .filter(data -> !bif.apply(data, compareBy))
                    .collect(Collectors.toList());
            Files.write(filepath, dataToWrite);
        } catch (IOException e) {
            throw new UserDaoException("ERROR delete data from file");
        }
    }

    public Optional<Doctor> getDoctorFromFile(String login, String password) {
        String doctorLine = findUserLineInFileByLoginPassword(login, password);
        return Optional.ofNullable(fileServiceDAO.createDoctor(doctorLine));
    }

    public Optional<Patient> getPatientFromFile(String login, String password) {
        String patientLine = findUserLineInFileByLoginPassword(login, password);
        return Optional.ofNullable(fileServiceDAO.createPatientFromLine(patientLine));
    }

    public Optional<Patient> getPatientFromFileByID(int id) {
        String patientLine = findUserLineInFileById(id);
        return Optional.ofNullable(fileServiceDAO.createPatientFromLine(patientLine));
    }

    private String findUserLineInFileByLoginPassword(String login, String password) {

        try {
            List<String> fileData = Files.readAllLines(Paths.get(Constants.FILE_PATH_USERS));

            return fileData
                    .stream()
                    .filter(s -> fileServiceDAO.isLoginPasswordCorrect(s, login, password))
                    .findFirst().orElseThrow(UserDaoException::new);

        } catch (IOException e) {
            throw new UserDaoException("ERROR with IOE");
        }
    }

    private String findUserLineInFileById(int id) {

        try {
            List<String> fileData = Files.readAllLines(Paths.get(Constants.FILE_PATH_USERS));

            return fileData
                    .stream()
                    .filter(s -> fileServiceDAO.isIdCorrect(s, id))
                    .findFirst().orElseThrow(UserDaoException::new);

        } catch (IOException e) {
            throw new UserDaoException("ERROR with IOE");
        }
    }

}
