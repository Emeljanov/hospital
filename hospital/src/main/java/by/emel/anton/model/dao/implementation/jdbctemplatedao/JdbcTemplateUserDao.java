package by.emel.anton.model.dao.implementation.jdbctemplatedao;

import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.beans.users.UserType;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.interfaces.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.LocalDate;

@Repository("UserJdbcTemplate")

public class JdbcTemplateUserDao implements UserDAO {

    private static final String SQL_IS_LOGIN_EXIST = "select count(*) from users where login = ? and id = ?";
    private static final String SQL_SAVE_USER =
    "insert into users (login,password,name,birthday,user_type) values (?,?,?, ?,?)";
    private static final String SQL_GET_USER_ID = "select id from users where login = ?";
    private static final String SQL_SAVE_DOCTOR = "insert into doctors (id_doctor) values (?)";
    private static final String SQL_SAVE_PATIENT = "insert into patients (id_patient, id_doctor) values (?,?)";
    private static final String SQL_UPDATE_USER = "update users set password = ?, name = ?, birthday = ? where id = ?";
    private static final String SQL_UPDATE_PATIENT = "update patients set id_doctor = ? where id_patient = ?";
    private static final String SQL_MAX_ID = "select max(id) from users";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean isLoginExist(String login) {
        int id = 2;
        int count = jdbcTemplate.queryForObject(SQL_IS_LOGIN_EXIST, new Object[]{login,id},Integer.class);
        return count >= 1;
    }

    @Override
    public void updateUser(User user) {
        int userId = getUsersId(user);
        UserType userType = user.getUserType();
        String password = user.getPassword();
        String name = user.getName();
        LocalDate birthday = user.getBirthday();

        jdbcTemplate.update(SQL_UPDATE_USER, password,name,birthday,userId);
        if(UserType.PATIENT == userType) {
            Patient patient = (Patient)user;
            int doctorId = patient.getDoctorId();
            jdbcTemplate.update(SQL_UPDATE_PATIENT,doctorId,userId);
        }
    }

    @Override
    public void saveUser(User user) {
        String login = user.getLogin();
        if(isLoginExist(login)) {
            return;
        }

        UserType userType = user.getUserType();
        String password = user.getPassword();
        String name = user.getName();
        LocalDate birthday = user.getBirthday();

        if (UserType.DOCTOR == userType) {

            jdbcTemplate.update(SQL_SAVE_USER,login,password,name,birthday.toString(),userType.toString());
            int userId = getUsersId(user);
            jdbcTemplate.update(SQL_SAVE_DOCTOR, userId);

        }
        else  if(UserType.PATIENT == userType) {
            Patient patient = (Patient) user;
            jdbcTemplate.update(SQL_SAVE_USER,login,password,name,birthday.toString(),userType.toString());
            int doctorId = patient.getDoctorId();
            int patientId = getUsersId(user);
            jdbcTemplate.update(SQL_SAVE_PATIENT,patientId,doctorId);
        }

    }

    @Override
    public int getNextId(User user) {
        Integer id =jdbcTemplate.queryForObject(SQL_MAX_ID,Integer.class);
        if( id == null) {
            return 1;
        }
        else {
            return id+1;
        }
    }

    private int getUsersId(User user) {
        String login = user.getLogin();
        return jdbcTemplate.queryForObject(SQL_GET_USER_ID, new Object[]{login},Integer.class);
    }
}