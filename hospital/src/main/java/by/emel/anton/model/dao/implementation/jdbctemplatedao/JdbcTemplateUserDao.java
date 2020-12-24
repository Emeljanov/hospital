package by.emel.anton.model.dao.implementation.jdbctemplatedao;

import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.interfaces.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository("UserJdbcTemplate")

public class JdbcTemplateUserDao implements UserDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//this method only for testing connection
    public String getStr() {
        String sql = "select name from users where id = 1";
        String s = jdbcTemplate.queryForObject(sql,String.class);
        return s;
    }

    @Override
    public boolean isLoginExist(String login) throws IOException {
        String sqlisLoginExist = "select count(*) from users where login = ? and id = ?";
        int id = 2;
        int count = jdbcTemplate.queryForObject(sqlisLoginExist, new Object[]{login,id},Integer.class);
        return count >= 1;
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
