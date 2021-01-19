package by.emel.anton.model.dao.implementation.jdbctemplatedao.rowmappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PatientIDMapper implements RowMapper<Integer> {
    @Override
    public Integer mapRow(ResultSet resultSet, int i) throws SQLException {

        int patientId = resultSet.getInt("id");
        return patientId;

    }
}
