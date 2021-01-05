package by.emel.anton.model.dao.implementation.jdbctemplatedao;

import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.implementation.jdbctemplatedao.rowmappers.DoctorIDMapper;
import by.emel.anton.model.dao.implementation.jdbctemplatedao.rowmappers.PatientMapper;
import by.emel.anton.model.dao.implementation.jdbctemplatedao.rowmappers.TherapyIDMapper;
import by.emel.anton.model.dao.interfaces.PatientDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Repository("PatientJdbcTemplate")
public class JdbcTemplatePatientDAO implements PatientDAO {

    private static final String SQL_GET_PATIENT = "select * from users where login = ? and password = ?";
    private static final String SQL_GET_PATIENT_BY_ID = "select * from users where id = ?";
    private static final String SQL_GET_DOCTOR_ID = "select * from patients where id_patient = ?";
    private static final String SQL_GET_THERAPIY_ID = "select id_therapy from therapies where id_patient = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplatePatientDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Patient> getPatient(String login, String password) throws UserDAOException{
        try {
            Patient patient = jdbcTemplate.queryForObject(SQL_GET_PATIENT, new Object[]{login,password},new PatientMapper());
            addDoctorAndTherapiesToPatient(patient);
            return Optional.of(patient);
        }
        catch (DataAccessException e) {
            throw new UserDAOException("ERROR getPatient");
        }

    }

    @Override
    public Optional<Patient> getPatientById(int id) throws UserDAOException {
        try {
            Patient patient = jdbcTemplate.queryForObject(SQL_GET_PATIENT_BY_ID, new Object[]{id}, new PatientMapper());
            addDoctorAndTherapiesToPatient(patient);
            return Optional.of(patient);
        }
        catch (DataAccessException e) {
            throw new UserDAOException("ERROR getPatientByID");
        }

    }

    private void addDoctorAndTherapiesToPatient(Patient patient) throws UserDAOException {
        try {
            int id_doctor = jdbcTemplate.queryForObject(SQL_GET_DOCTOR_ID, new Object[]{patient.getId()},new DoctorIDMapper());
            patient.setDoctorId(id_doctor);
            List<Integer> therapies_id = jdbcTemplate.query(SQL_GET_THERAPIY_ID, new TherapyIDMapper(),patient.getId());
            patient.setTherapies(therapies_id);
        }
        catch (DataAccessException e) {
            throw  new UserDAOException("ERROR add Doc to Pat");
        }

    }
}
