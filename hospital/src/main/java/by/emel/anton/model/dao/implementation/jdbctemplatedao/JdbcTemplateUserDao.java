package by.emel.anton.model.dao.implementation.jdbctemplatedao;

import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.dao.interfaces.UserDAO;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.IOException;

public class JdbcTemplateUserDao implements UserDAO {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean isLoginExist(String login, String filePath) throws IOException {
        return false;
    }

    @Override
    public void updateUser(User user) throws IOException {

    }

    @Override
    public void saveUser(User user) throws IOException {

    }

    @Override
    public int getNextId(User user) throws IOException {
        return 0;
    }
}
