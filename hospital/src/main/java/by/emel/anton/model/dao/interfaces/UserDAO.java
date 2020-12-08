package by.emel.anton.model.dao.interfaces;


import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.beans.users.UserType;


public interface UserDAO {

    boolean isLoginExist(String login, String filePath);

    void updateUser(User user);

    void saveUser(User user);

    int getNextId(User user);


}
