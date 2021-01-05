package by.emel.anton.model.dao.implementation.filedoa;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.beans.users.UserType;
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
import java.util.stream.Collectors;

@Repository("UserFromFile")
public class FileUserDAO implements UserDAO {

    public boolean isLoginExist(String login) throws UserDAOException {

        try {
            List<String> fileDataDoctors = Files.readAllLines(Paths.get(Constants.FILE_PATH_DOCTORS));
            boolean answer = fileDataDoctors.stream().anyMatch(s -> isLoginExistFilter(s,login));

            if(answer) return true;

            else {
                List<String> fileDataPatients = Files.readAllLines(Paths.get(Constants.FILE_PATH_PATIENTS));
                return fileDataPatients.stream().anyMatch(s -> isLoginExistFilter(s,login));
            }
        }
        catch (IOException e) {
            throw new UserDAOException("ERROR in method isLoginExist in file");
        }

    }

    @Override
    public void saveUser(User user) throws UserDAOException {

        String login = user.getLogin();
        UserType userType = user.getUserType();

        if(UserType.DOCTOR == userType) {

            if (isLoginExist(login)) {
                return;
            }

            saveUserInFile(user,Constants.FILE_PATH_DOCTORS);

        }

        else if (UserType.PATIENT == userType) {

            if(isLoginExist(login)) {
                return;
            }

            saveUserInFile(user,Constants.FILE_PATH_PATIENTS);

        }
    }

    @Override
    public int getNextId(User user) throws UserDAOException {

        String filePath = Constants.EMPTY;
        UserType userType = user.getUserType();

        if(userType == UserType.DOCTOR) {
            filePath = Constants.FILE_PATH_DOCTORS;
        }
        else if(userType == UserType.PATIENT) {
            filePath = Constants.FILE_PATH_PATIENTS;
        }

        try {
            return FileService.getNextLineId(filePath);
        }
        catch (IOException e) {
            throw new UserDAOException("ERROR getNextId from file");
        }

    }

    @Override
    public void updateUser(User user) throws UserDAOException {

        String login = user.getLogin();
        UserType userType = user.getUserType();
        Path userFilePath = null;

        if(UserType.DOCTOR == userType) {

            userFilePath = Paths.get(Constants.FILE_PATH_DOCTORS);

        }

        else if(UserType.PATIENT == userType) {

            userFilePath = Paths.get(Constants.FILE_PATH_PATIENTS);

        }

        try {
            List<String> fileData = Files.readAllLines(userFilePath);

            List<String> linesToWrite = fileData
                    .stream()
                    .filter(s -> !isLoginExistFilter(s,login))
                    .collect(Collectors.toList());

            Files.write(userFilePath,linesToWrite);
        }
        catch (IOException e) {
            throw new UserDAOException("ERROR update user in file");
        }

        saveUser(user);

    }

    private boolean isLoginExistFilter(String line, String login) {

        String[] lineData = line.split(Constants.SEPARATOR);
        return login.equals(lineData[1]);

    }

    private void saveUserInFile(User user, String filePath) throws UserDAOException {

        List<String> lines = Collections.singletonList(user.toString());
        try {
            Files.write(Paths.get(filePath), lines, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new UserDAOException("ERROR save user in file");
        }

    }

}
