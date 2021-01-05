package by.emel.anton.model.dao.interfaces;

import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.dao.exceptions.UserDAOException;

import java.io.IOException;

public interface UserDAO {

//    boolean isLoginExist(String login, String filePath) throws IOException;
    boolean isLoginExist(String login) throws UserDAOException;

    void updateUser(User user) throws UserDAOException;

    void saveUser(User user) throws UserDAOException;

    int getNextId(User user) throws UserDAOException;


}
