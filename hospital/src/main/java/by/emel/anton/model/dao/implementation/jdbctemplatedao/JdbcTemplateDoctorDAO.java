package by.emel.anton.model.dao.implementation.jdbctemplatedao;

import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.interfaces.DoctorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Repository("DoctorJdbcTemplate")
public class JdbcTemplateDoctorDAO implements DoctorDAO {

    private static String SQL_GET_DOCTOR = "select * from users where login = ? and password = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void JdbcTemplateUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Optional<Doctor> getDoctor(String login, String password) throws UserDAOException, IOException {

     Doctor doctor = jdbcTemplate.queryForObject(SQL_GET_DOCTOR,new Object[]{login,password}, new DoctorMapper());

     return Optional.ofNullable(doctor);
    }
}
