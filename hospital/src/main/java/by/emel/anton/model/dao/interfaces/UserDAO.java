package by.emel.anton.model.dao.interfaces;

import by.emel.anton.model.entity.users.User;

import java.util.Optional;

public interface UserDAO {

    boolean isLoginExist(String login);

    void updateUser(User user);

    void saveUser(User user);

    Optional<User> getSimpleUserByLogin(String login);

}
