package by.emel.anton.model.dao.implementation.jdbctemplatedao;

import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.beans.users.UserType;
import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDaoException;
import by.emel.anton.model.dao.interfaces.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository("JdbcTemplateUserDAO")

public class JdbcTemplateUserDao implements UserDAO {

    private static final String SQL_IS_LOGIN_EXIST = "select count(*) from user where login = ?";
    private static final String SQL_SAVE_USER =
            "insert into user (login,password,name,birthday,user_type) values (?,?,?, ?,?)";
    private static final String SQL_GET_USER_ID = "select id from user where login = ?";
    private static final String SQL_SAVE_DOCTOR = "insert into doctor (id) values (?)";
    private static final String SQL_SAVE_PATIENT = "insert into patient (id, doctor_id) values (?,?)";
    private static final String SQL_SAVE_PATIENT_NO_SETTED_DOCTOR = "insert into patient (id) values (?)";
    private static final String SQL_UPDATE_USER = "update user set password = ?, name = ?, birthday = ? where id = ?";
    private static final String SQL_UPDATE_PATIENT = "update patient set doctor_id = ? where id = ?";
    private static final String SQL_MAX_ID = "select max(id) from user";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean isLoginExist(String login) {

        int count = jdbcTemplate.queryForObject(SQL_IS_LOGIN_EXIST, new Object[]{login}, Integer.class);
        return count >= 1;
    }

    @Override
    public void updateUser(User user) {
        int userId = getUsersId(user);
        UserType userType = user.getUserType();
        String password = user.getPassword();
        String name = user.getName();
        LocalDate birthday = user.getBirthday();

        jdbcTemplate.update(SQL_UPDATE_USER, password, name, birthday, userId);

        if (UserType.PATIENT == userType) {
            Patient patient = (Patient) user;
            int doctorId = patient.getDoctor().getId();

            jdbcTemplate.update(SQL_UPDATE_PATIENT, doctorId, userId);
        }
    }

    @Override
    public void saveUser(User user) {
        String login = user.getLogin();
        if (isLoginExist(login)) {
            throw new UserDaoException();
        }

        UserType userType = user.getUserType();
        String password = user.getPassword();
        String name = user.getName();
        LocalDate birthday = user.getBirthday();

        if (UserType.DOCTOR == userType) {

            jdbcTemplate.update(SQL_SAVE_USER, login, password, name, birthday.toString(), userType.toString());
            int userId = getUsersId(user);
            jdbcTemplate.update(SQL_SAVE_DOCTOR, userId);

        } else if (UserType.PATIENT == userType) {
            Patient patient = (Patient) user;
            jdbcTemplate.update(SQL_SAVE_USER, login, password, name, birthday.toString(), userType.toString());
            int patientId = getUsersId(user);
            Optional<Doctor> doctor = Optional.ofNullable(patient.getDoctor());
            doctor.ifPresentOrElse(d -> {
                jdbcTemplate.update(SQL_SAVE_PATIENT, patientId, d.getId());
            }, () -> jdbcTemplate.update(SQL_SAVE_PATIENT_NO_SETTED_DOCTOR, patientId));
        }
    }

    private int getUsersId(User user) {
        String login = user.getLogin();
        return jdbcTemplate.queryForObject(SQL_GET_USER_ID, new Object[]{login}, Integer.class);
    }
}
