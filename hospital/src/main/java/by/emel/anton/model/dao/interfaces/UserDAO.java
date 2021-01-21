package by.emel.anton.model.dao.interfaces;

import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.dao.exceptions.UserDAOException;

public interface UserDAO {

    boolean isLoginExist(String login) throws UserDAOException;

    void updateUser(User user) throws UserDAOException;

    void saveUser(User user) throws UserDAOException;

}
