package by.emel.anton.model.dao.implementation.jdbctemplatedao;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.implementation.jdbctemplatedao.rowmappers.DoctorMapper;
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
    private static final String SQL_GET_DOCTOR_BY_ID = "select * from user where id = ?";
    private static final String SQL_GET_PATIENT = "select u.*, p.doctor_id from user u inner join patient p ON p.id = u.id and p.doctor_id = ?";

    private JdbcTemplate jdbcTemplate;
    private DoctorMapper doctorMapper;
    private PatientMapper patientMapper;

    @Autowired
    public void JdbcTemplateUserDao(
            JdbcTemplate jdbcTemplate,
            DoctorMapper doctorMapper,
            PatientMapper patientMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.doctorMapper = doctorMapper;
        this.patientMapper = patientMapper;
    }

    @Override
    public Optional<Doctor> getDoctor(String login, String password) throws UserDAOException {

     try {
         Doctor doctor = jdbcTemplate.queryForObject(SQL_GET_DOCTOR,new Object[]{login,password},doctorMapper);
         addPatients(doctor);
         return Optional.of(doctor);

     }
     catch (DataAccessException e) {
         throw new UserDAOException(Constants.EXCEPTION_MESSAGE_LP_INCORRECT);
     }
    }
    public Optional<Doctor> getDoctorById (int id) throws UserDAOException {
        try {
            Optional<Doctor> doctor = Optional.ofNullable(jdbcTemplate.queryForObject(SQL_GET_DOCTOR_BY_ID, new Object[]{id},doctorMapper));
            doctor.ifPresent(this::addPatients);
            return doctor;
        }
        catch (DataAccessException e) {
            throw new UserDAOException("ERROR getDoctorByID");
        }

    }

    private void addPatients(Doctor doctor) {
        List<Patient> patients = jdbcTemplate.query(SQL_GET_PATIENT,patientMapper,doctor.getId());
        patients.forEach(patient -> {
            patient.setDoctor(doctor);
            JdbcTemplateService.addTherapiesToPatient(patient,jdbcTemplate);
        } );
        doctor.setPatients(patients);
    }
}
