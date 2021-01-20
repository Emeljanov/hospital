package by.emel.anton.model.dao.implementation.jdbctemplatedao;

import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.implementation.jdbctemplatedao.rowmappers.TherapyMapper;
import by.emel.anton.model.dao.interfaces.TherapyDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository("TherapyJdbcTemplate")
public class JdbcTemplateTherapyDAO implements TherapyDAO {
    private static final String SQL_SAVE_THERAPY =
            "insert into therapy (dtype ,description, start_date, end_date,patient_id) values (?,?, ?,?, ?)";
    private static final String SQL_GET_THERAPY_BY_ID = "select * from therapy where id = ?";

    JdbcTemplate jdbcTemplate;
    JdbcTemplatePatientDAO jdbcTemplatePatientDAO;

    @Autowired
    public JdbcTemplateTherapyDAO(JdbcTemplate jdbcTemplate,JdbcTemplatePatientDAO jdbcTemplatePatientDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcTemplatePatientDAO = jdbcTemplatePatientDAO;
    }

    @Override
    public void saveTherapy(Therapy therapy) {
        String desc = therapy.getDescription();
        LocalDate startDate = therapy.getStartDate();
        LocalDate endDate = therapy.getEndDate();
        int patientId = therapy.getPatient().getId();
        String dtype = therapy.getClass().getSimpleName();

        jdbcTemplate.update(SQL_SAVE_THERAPY,dtype,desc,startDate,endDate,patientId);
    }

    @Override
    public Optional<Therapy> getTherapy(int id) throws UserDAOException {

        Therapy therapy = jdbcTemplate.queryForObject(SQL_GET_THERAPY_BY_ID, new Object[]{id}, new TherapyMapper());
        Optional<Patient> patient = jdbcTemplatePatientDAO.getPatientById(therapy.getPatient().getId());
        patient.ifPresent(pat -> therapy.setPatient(pat));
        return Optional.ofNullable(therapy);

    }
}
