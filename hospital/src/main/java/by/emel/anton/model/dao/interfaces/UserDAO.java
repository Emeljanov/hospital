package by.emel.anton.model.dao.interfaces;

import by.emel.anton.constants.Constans;
import by.emel.anton.model.beans.users.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public interface UserDAO {

    boolean isLoginExist(String login, String filePath);

}
