package by.emel.anton.model.dao.implementation.jdbctemplatedao;

import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.dao.interfaces.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplateUserDao implements UserDAO {

//    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;


    /*@Autowired
    public JdbcTemplateUserDao(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;

    }*/


    public List<Map<String,Object>> getUsers() {
        return jdbcTemplate.queryForList("select * from users");

    }
    public void getCon() {
        System.out.println(jdbcTemplate.getMaxRows());

    }

    public String getStr() {
        String sql = "select name from users where id = 1";
        String s = jdbcTemplate.queryForObject(sql,String.class);
        return s;
    }

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
