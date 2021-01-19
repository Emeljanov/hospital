package by.emel.anton.model.dao.implementation.jdbctemplatedao;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.implementation.jdbctemplatedao.rowmappers.DoctorMapper;
import by.emel.anton.model.dao.implementation.jdbctemplatedao.rowmappers.PatientIDMapper;
import by.emel.anton.model.dao.interfaces.DoctorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository("DoctorJdbcTemplate")
public class JdbcTemplateDoctorDAO implements DoctorDAO {

    private static final String SQL_GET_DOCTOR = "select * from users where login = ? and password = ?";
    private static final String SQL_GET_PATIENT_IDS = "select id_patient from patients where id_doctor = ?";


    private JdbcTemplate jdbcTemplate;
    private DoctorMapper doctorMapper;
    private PatientIDMapper patientIDMapper;

    @Autowired
    public void JdbcTemplateUserDao(JdbcTemplate jdbcTemplate,DoctorMapper doctorMapper, PatientIDMapper patientIDMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.doctorMapper = doctorMapper;
        this.patientIDMapper = patientIDMapper;
    }


    @Override
    public Optional<Doctor> getDoctor(String login, String password) throws UserDAOException {

     try {
         Doctor doctor = jdbcTemplate.queryForObject(SQL_GET_DOCTOR,new Object[]{login,password},doctorMapper);
         List<Integer> patient_ids = jdbcTemplate.query(SQL_GET_PATIENT_IDS,patientIDMapper,doctor.getId());
         doctor.setPatientsId(patient_ids);

         return Optional.of(doctor);

     }
     catch (DataAccessException e) {
         throw new UserDAOException(Constants.EXCEPTION_MESSAGE_LP_INCORRECT);
     }
    }
}
