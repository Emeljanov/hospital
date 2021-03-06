package by.emel.anton.model.dao.implementation.jdbctemplatedao.rowmappers;

import by.emel.anton.model.entity.therapy.Therapy;
import by.emel.anton.model.entity.users.patients.Patient;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TherapyMapper implements RowMapper<Therapy> {
    @Override
    public Therapy mapRow(ResultSet resultSet, int i) throws SQLException {
        Therapy therapy = new Therapy();
        therapy.setId(resultSet.getInt("id"));
        therapy.setStartDate(resultSet.getDate("start_date").toLocalDate());
        therapy.setEndDate(resultSet.getDate("end_date").toLocalDate());
        therapy.setDescription(resultSet.getString("description"));
//this is only for patient ID, then it will be replace by the real patient
        Patient patient = new Patient();
        patient.setId(resultSet.getInt("patient_id"));
        therapy.setPatient(patient);

        return therapy;
    }
}
