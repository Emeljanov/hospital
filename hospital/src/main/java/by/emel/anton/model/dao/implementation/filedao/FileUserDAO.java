package by.emel.anton.model.dao.implementation.filedao;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.beans.users.UserType;
import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.interfaces.UserDAO;
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

@Repository("UserFromFile")
public class FileUserDAO implements UserDAO {

    private final Path userPath = Paths.get(Constants.FILE_PATH_USERS);
    private final Path patientPath = Paths.get(Constants.FILE_PATH_PATIENTS);
    private final Path doctorPath = Paths.get(Constants.FILE_PATH_DOCTORS);

    private final BiFunction<String,String, Boolean> bifIsLoginExist = (line, login) -> login.equals(line.split(Constants.SEPARATOR)[1]);
    private final BiFunction<String,String, Boolean> bifIsIdExist = (line, idString) -> idString.equals(line.split(Constants.SEPARATOR)[0]);

    private void FilterAndDeleteDataFromFile(Path filepath,String compareWith ,BiFunction<String, String, Boolean> bif) throws UserDAOException {
        try {
            List<String> dataFromFile = Files.readAllLines(filepath);
            List<String> dataToWrite = dataFromFile.stream().filter(data -> bif.apply(data,compareWith)).collect(Collectors.toList());
        } catch (IOException e) {
            throw new UserDAOException("ERROR delete data from file");
        }
    }

    public boolean isLoginExist(String login) throws UserDAOException {

        try {
            List<String> fileDataUsers = Files.readAllLines(userPath);

            return fileDataUsers
                    .stream()
                    .anyMatch(s -> isLoginExistFilter(s, login));
        } catch (IOException e) {
            throw new UserDAOException("ERROR in method isLoginExist in file");
        }
    }

    @Override
    public void saveUser(User user) throws UserDAOException {

        String idStr = String.valueOf(FileService.getNextLineId(userPath);
        UserType userType = user.getUserType();
        String login = user.getLogin();
        String password = user.getPassword();
        String birthday = user.getBirthday().toString();

        if (isLoginExist(login)) {
            return;
        }

        String lineUserForWrite = String.join(Constants.SEPARATOR, idStr, login, password, birthday, userType.toString());
        writeInFile(lineUserForWrite, userPath);

        if (UserType.DOCTOR == userType) {

            writeInFile(idStr, doctorPath);

        } else if (UserType.PATIENT == userType) {

            Patient patient = (Patient) user;
            Optional<Doctor> doctor = Optional.ofNullable(patient.getDoctor());
            String doctorIdLine = "null";

            if (doctor.isPresent()) {
                doctorIdLine = String.valueOf(doctor.get().getId());
            }

            String linePatientToWrite = String.join(Constants.SEPARATOR, idStr, doctorIdLine);
            writeInFile(linePatientToWrite, patientPath);
        }
    }

    @Override
    public void updateUser(User user) throws UserDAOException {

        String login = user.getLogin();
        UserType userType = user.getUserType();

        try {
            List<String> fileData = Files.readAllLines(userPath);
            List<String> linesToWrite = fileData
                    .stream()
                    .filter(s -> !isLoginExistFilter(s, login))
                    .collect(Collectors.toList());

            Files.write(userPath, linesToWrite);
        } catch (IOException e) {
            throw new UserDAOException("ERROR update user in file");
        }

        saveUser(user);

    }

    private boolean isLoginExistFilter(String line, String login) {

        String[] lineData = line.split(Constants.SEPARATOR);
        return login.equals(lineData[1]);

    }

    private boolean isIdExistFilter(String line, String id) {
        /*StringCompareValue stringCompareValue = line1 -> line1.contains("s");
        stringCompareValue.isExistLine(String.valueOf(id));*/



        String[] lineData = line.split(Constants.SEPARATOR);
        return (String.valueOf(id) == lineData[0]);
    }




    private void writeInFile(String writeLine, Path filePath) throws UserDAOException {
        List<String> lines = Collections.singletonList(writeLine);
        try {
            Files.write(filePath, lines, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new UserDAOException("ERROR : can't save user in file");
        }
    }

    private void deleteUserFromFiles(User user) throws UserDAOException {
        String login = user.getLogin();
        UserType userType = user.getUserType();
        String userId = String.valueOf(user.getId());

        try {
            FilterAndDeleteDataFromFile(userPath,login, bifIsLoginExist);

           /* List<String> userFileData = Files.readAllLines(userPath);
            List<String> linesToWrite = userFileData
                    .stream()
                    .filter(s -> !isLoginExistFilter(s, login))
                    .collect(Collectors.toList());

            Files.write(userPath, linesToWrite);*/

            if (userType == UserType.DOCTOR) {
                FilterAndDeleteDataFromFile(doctorPath,userId,hyi2);


               /* List<String> doctorFileData = Files.readAllLines(doctorPath);
                List<String> doctorLinesToWrite = doctorFileData
                        .stream()
                        .filter(d -> hyi.apply(d, String.valueOf(userId)))
//                        .filter(d -> !isIdExistFilter(d, userId))
                        .collect(Collectors.toList());
                Files.write(doctorPath, doctorLinesToWrite);*/
            } else if (userType == UserType.PATIENT) {
                List<String> patientFileData = Files.readAllLines(patientPath);
                List<String> patientLinesToWrite = patientFileData
                        .stream()
                        .filter(p -> !isIdExistFilter(p, String.valueOf(userId)))
                        .collect(Collectors.toList());
                Files.write(patientPath, patientLinesToWrite);
            }

        } catch (IOException e) {
            throw new UserDAOException("ERROR update user in file");
        }
    }





}
