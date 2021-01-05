package by.emel.anton.model.dao.implementation.jdbctemplatedao.rowmappers;


import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.beans.users.doctors.GeneralDoctor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorMapper implements RowMapper<Doctor> {
    @Override
    public Doctor mapRow(ResultSet resultSet, int i) throws SQLException {
        Doctor doctor = new GeneralDoctor();
        doctor.setId(resultSet.getInt("id"));
        doctor.setLogin(resultSet.getString("login"));
        doctor.setPassword(resultSet.getString("password"));
        doctor.setName(resultSet.getString("name"));
        doctor.setBirthday(resultSet.getDate("birthday").toLocalDate());

        return doctor;
    }
}
