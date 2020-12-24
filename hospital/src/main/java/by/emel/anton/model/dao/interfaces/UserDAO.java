package by.emel.anton.model.dao.interfaces;

import by.emel.anton.model.beans.users.User;

import java.io.IOException;

public interface UserDAO {

//    boolean isLoginExist(String login, String filePath) throws IOException;
    boolean isLoginExist(String login) throws IOException;

    void updateUser(User user) throws IOException;

    void saveUser(User user) throws IOException;

    int getNextId(User user) throws IOException;


}
