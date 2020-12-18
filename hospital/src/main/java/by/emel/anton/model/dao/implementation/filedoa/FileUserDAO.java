package by.emel.anton.model.dao.implementation.filedoa;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.beans.users.UserType;
import by.emel.anton.model.dao.interfaces.UserDAO;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FileUserDAO implements UserDAO {

    public boolean isLoginExist(String login, String filePath) throws IOException {

        List<String> fileData = Files.readAllLines(Paths.get(filePath));

        return  fileData.stream().anyMatch(s -> isLoginExistFilter(s,login));

    }

    @Override
    public void saveUser(User user) throws IOException {

        String login = user.getLogin();
        UserType userType = user.getUserType();

        if(UserType.DOCTOR == userType) {

            if (isLoginExist(login, Constants.FILE_PATH_DOCTORS)) {
                return;
            }

            saveUserInFile(user,Constants.FILE_PATH_DOCTORS);

        }

        else if (UserType.PATIENT == userType) {

            if(isLoginExist(login, Constants.FILE_PATH_PATIENTS)) {
                return;
            }

            saveUserInFile(user,Constants.FILE_PATH_PATIENTS);

        }
    }

    @Override
    public int getNextId(User user) throws IOException {

        String filePath = Constants.EMPTY;
        UserType userType = user.getUserType();

        if(userType == UserType.DOCTOR) {
            filePath = Constants.FILE_PATH_DOCTORS;
        }
        else if(userType == UserType.PATIENT) {
            filePath = Constants.FILE_PATH_PATIENTS;
        }

        return FileService.getNextLineId(filePath);
    }

    @Override
    public void updateUser(User user) throws IOException {

        String login = user.getLogin();
        UserType userType = user.getUserType();
        Path userFilePath = null;

        if(UserType.DOCTOR == userType) {

            userFilePath = Paths.get(Constants.FILE_PATH_DOCTORS);

        }

        else if(UserType.PATIENT == userType) {

            userFilePath = Paths.get(Constants.FILE_PATH_PATIENTS);

        }

        List<String> fileData = Files.readAllLines(userFilePath);

        List<String> linesToWrite = fileData
                .stream()
                .filter(s -> !isLoginExistFilter(s,login))
                .collect(Collectors.toList());

        Files.write(userFilePath,linesToWrite);
        saveUser(user);

    }

    private boolean isLoginExistFilter(String line, String login) {

        String[] lineData = line.split(Constants.SEPARATOR);
        return login.equals(lineData[1]);

    }

    private void saveUserInFile(User user, String filePath) throws IOException {

        List<String> lines = Collections.singletonList(user.toString());
        Files.write(Paths.get(filePath), lines, StandardOpenOption.APPEND);

    }

}
