package by.emel.anton.model.dao.implementation.filedao;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.dao.exceptions.UserDAOException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileService {
    public static boolean isLoginPasswordCorrect(String data, String login, String password) {
        String[] userData = data.split(Constants.SEPARATOR);
        String loginData = userData[1];
        String passwordData = userData[2];

        return loginData.equals(login) && passwordData.equals(password);
    }

    public static boolean findLineById(String data, int id) {
        String[] userData = data.split(Constants.SEPARATOR);
        int idData = Integer.parseInt(userData[0]);

        return id == idData;
    }

    public static int getNextLineId(Path filePath) throws UserDAOException {

        try {
            int nextId = 1;
            List<String> fileData = Files.readAllLines(filePath);
            for (String s : fileData) {
                int lineId = Integer.parseInt(s.split(Constants.SEPARATOR)[0]);
                if (lineId >= nextId) {
                    nextId = lineId + 1;
                }
            }
            return nextId;
        } catch (IOException e) {
            throw new UserDAOException("ERROR get next line id");
        }
    }
}
