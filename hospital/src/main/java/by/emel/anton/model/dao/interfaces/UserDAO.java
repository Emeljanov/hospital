package by.emel.anton.model.dao.interfaces;

import by.emel.anton.model.beans.users.User;

public interface UserDAO {

    boolean isLoginExist(String login);

    void updateUser(User user);

    void saveUser(User user);

}
