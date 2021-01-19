package by.emel.anton.model.dao.implementation.jdbctemplatedao;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.implementation.jdbctemplatedao.rowmappers.DoctorMapper;
import by.emel.anton.model.dao.implementation.jdbctemplatedao.rowmappers.PatientIDMapper;
import by.emel.anton.model.dao.implementation.jdbctemplatedao.rowmappers.PatientMapper;
import by.emel.anton.model.dao.interfaces.DoctorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository("DoctorJdbcTemplate")
public class JdbcTemplateDoctorDAO implements DoctorDAO {

    private static final String SQL_GET_DOCTOR = "select * from user where login = ? and password = ?";
    private static final String SQL_GET_PATIENT_IDS = "select id from patient where doctor_id = ?";
    private static final String SQL_GET_PATIENT = "select u.* from user u inner join patient p ON p.id = u.id and p.doctor_id = ?";



    private JdbcTemplate jdbcTemplate;
    private DoctorMapper doctorMapper;
    private PatientIDMapper patientIDMapper;
    private PatientMapper patientMapper;

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
//достать всех пациентов и засунуть им этого доктора
//         List<Integer> patientIds = jdbcTemplate.query(SQL_GET_PATIENT_IDS,patientIDMapper,doctor.getId());
         List<Patient> patients = jdbcTemplate.query(SQL_GET_PATIENT,patientMapper,doctor.getId());
         patients.stream().forEach(patient -> patient.setDoctor(doctor));
//сюда добавить все терапии к пациенту
//         doctor.setPatientsId(patientIds);

         return Optional.of(doctor);

     }
     catch (DataAccessException e) {
         throw new UserDAOException(Constants.EXCEPTION_MESSAGE_LP_INCORRECT);
     }
    }
}
