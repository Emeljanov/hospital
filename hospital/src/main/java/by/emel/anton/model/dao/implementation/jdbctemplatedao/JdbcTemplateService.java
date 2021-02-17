package by.emel.anton.model.dao.implementation.jdbctemplatedao;

import by.emel.anton.model.entity.therapy.Therapy;
import by.emel.anton.model.entity.users.patients.Patient;
import by.emel.anton.model.dao.implementation.jdbctemplatedao.rowmappers.TherapyMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcTemplateService {
    private static final String SQL_GET_THERAPIES = "select * from therapy where patient_id = ?";

    protected static void addTherapiesToPatient(Patient patient, JdbcTemplate jdbcTemplate) {
        List<Therapy> therapies = jdbcTemplate.query(SQL_GET_THERAPIES, new TherapyMapper(), patient.getId());
        therapies.forEach(t -> t.setPatient(patient));
        patient.setTherapies(therapies);
    }
}
