package by.emel.anton.model.dao.implementation.jdbctemplatedao;

import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.implementation.jdbctemplatedao.rowmappers.DoctorMapper;
import by.emel.anton.model.dao.implementation.jdbctemplatedao.rowmappers.PatientIDMapper;
import by.emel.anton.model.dao.interfaces.DoctorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Repository("DoctorJdbcTemplate")
public class JdbcTemplateDoctorDAO implements DoctorDAO {

    private static final String SQL_GET_DOCTOR = "select * from users where login = ? and password = ?";
    private static final String SQL_GET_PATIENT_IDS = "select id_patient from patients where id_doctor = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void JdbcTemplateUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Optional<Doctor> getDoctor(String login, String password) throws UserDAOException {

     try {
         Doctor doctor = jdbcTemplate.queryForObject(SQL_GET_DOCTOR,new Object[]{login,password}, new DoctorMapper());
         List<Integer> patient_ids = jdbcTemplate.query(SQL_GET_PATIENT_IDS,new PatientIDMapper(),doctor.getId());
         doctor.setPatientsId(patient_ids);

         return Optional.of(doctor);

     }
     catch (DataAccessException e) {
         throw new UserDAOException("ERROR with login or password");
     }
    }
}
