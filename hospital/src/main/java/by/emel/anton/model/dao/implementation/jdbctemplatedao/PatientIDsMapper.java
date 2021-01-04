package by.emel.anton.model.dao.implementation.jdbctemplatedao;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PatientIDsMapper implements RowMapper<List<Integer>> {
    @Override
    public List<Integer> mapRow(ResultSet resultSet, int i) throws SQLException {
        return null;
    }
}
