package by.emel.anton.model.dao.implementation.jdbctemplatedao.rowmappers;

import by.emel.anton.model.beans.users.patients.OrdinaryPatient;
import by.emel.anton.model.beans.users.patients.Patient;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PatientMapper implements RowMapper<Patient> {
    @Override
    public Patient mapRow(ResultSet resultSet, int i) throws SQLException {
        Patient patient = new OrdinaryPatient();
        patient.setId(resultSet.getInt("id"));
        patient.setLogin(resultSet.getString("login"));
        patient.setPassword(resultSet.getString("password"));
        patient.setName(resultSet.getString("name"));
        patient.setBirthday(resultSet.getDate("birthday").toLocalDate());
        return patient;
    }
}
