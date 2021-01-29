package by.emel.anton.model.dao.interfaces;

import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.dao.exceptions.UserDaoUncheckedException;

public interface UserDAO {

    boolean isLoginExist(String login) throws UserDaoUncheckedException;

    void updateUser(User user) throws UserDaoUncheckedException;

    void saveUser(User user) throws UserDaoUncheckedException;

}
