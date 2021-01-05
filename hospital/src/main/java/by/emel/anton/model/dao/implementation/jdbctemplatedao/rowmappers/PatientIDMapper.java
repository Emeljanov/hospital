package by.emel.anton.model.dao.implementation.jdbctemplatedao.rowmappers;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientIDMapper implements RowMapper<Integer> {
    @Override
    public Integer mapRow(ResultSet resultSet, int i) throws SQLException {

        int patient_id = resultSet.getInt("id_patient");
        return patient_id;

    }
}
