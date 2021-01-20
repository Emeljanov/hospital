package by.emel.anton.model.dao.implementation.jdbctemplatedao.rowmappers;

import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.beans.users.patients.Patient;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class PatientMapper implements RowMapper<Patient> {
    @Override
    public Patient mapRow(ResultSet resultSet, int i) throws SQLException {
        Patient patient = new Patient();
        patient.setId(resultSet.getInt("id"));
        patient.setLogin(resultSet.getString("login"));
        patient.setPassword(resultSet.getString("password"));
        patient.setName(resultSet.getString("name"));
        patient.setBirthday(resultSet.getDate("birthday").toLocalDate());
//this is only for doctor ID, then it will be replace by the real doctor <code 0!!!!!!!!!!!>
        int doctorId = resultSet.getInt("doctor_id");
        if (doctorId != 0) {
            Doctor doctor = new Doctor();
            doctor.setId(doctorId);
            patient.setDoctor(doctor);
        }
        return patient;
    }
}
