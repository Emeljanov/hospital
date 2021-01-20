package by.emel.anton.model.dao.implementation.jdbctemplatedao;

import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.implementation.jdbctemplatedao.rowmappers.PatientMapper;
import by.emel.anton.model.dao.interfaces.PatientDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("PatientJdbcTemplate")
public class JdbcTemplatePatientDAO implements PatientDAO {
    private static final String SQL_GET_PATIENT = "select u.*, p.doctor_id from user u inner join patient p ON u.id = p.id and u.login = ? and u.password = ?";
    private static final String SQL_GET_PATIENT_BY_ID = "select u.*, p.doctor_id from user u inner join patient p ON u.id = p.id and u.id = ?";

    private JdbcTemplate jdbcTemplate;
    private PatientMapper patientMapper;
    private JdbcTemplateDoctorDAO jdbcTemplateDoctorDAO;

    @Autowired
    public JdbcTemplatePatientDAO(JdbcTemplate jdbcTemplate, PatientMapper patientMapper, JdbcTemplateDoctorDAO jdbcTemplateDoctorDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.patientMapper = patientMapper;
        this.jdbcTemplateDoctorDAO = jdbcTemplateDoctorDAO;
    }

    @Override
    public Optional<Patient> getPatient(String login, String password) throws UserDAOException{
        try {
            Patient patient = jdbcTemplate.queryForObject(SQL_GET_PATIENT, new Object[]{login,password},patientMapper);
            addDoctorAndTherapiesToPatient(patient,jdbcTemplate);
            return Optional.of(patient);
        }
        catch (DataAccessException e) {
            throw new UserDAOException("ERROR getPatient");
        }

    }

    @Override
    public Optional<Patient> getPatientById(int id) throws UserDAOException {
        try {
            Patient patient = jdbcTemplate.queryForObject(SQL_GET_PATIENT_BY_ID, new Object[]{id}, patientMapper);
            addDoctorAndTherapiesToPatient(patient,jdbcTemplate);
            return Optional.of(patient);
        }
        catch (DataAccessException e) {
            throw new UserDAOException("ERROR getPatientByID");
        }
    }

    private void addDoctorAndTherapiesToPatient(Patient patient,JdbcTemplate jdbcTemplate)  {
        Doctor doctor = jdbcTemplateDoctorDAO.getDoctorById(patient.getDoctor().getId());
        patient.setDoctor(doctor);
        JdbcTemplateService.addTherapiesToPatient(patient,jdbcTemplate);
    }

}