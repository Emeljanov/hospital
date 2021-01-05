package by.emel.anton.model.dao.implementation.jdbctemplatedao;

import by.emel.anton.model.beans.therapy.Therapy;
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
            "insert into therapies (description, start_date, end_date, id_patient) values (?, ?,?, ?)";
    private static final String SQL_GET_THERAPY_BY_ID = "select * from therapies where id_therapy = ?";
    private static final String SQL_GET_MAX_ID = "select max(id_therapy) from therapies";

    JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateTherapyDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveTherapy(Therapy therapy) {
        String desc = therapy.getDescription();
        LocalDate startDate = therapy.getStartDate();
        LocalDate endDate = therapy.getEndDate();
        int id_patient = therapy.getId_patient();

        jdbcTemplate.update(SQL_SAVE_THERAPY,desc,startDate,endDate,id_patient);
    }

    @Override
    public int getNextID() {
        Integer id =jdbcTemplate.queryForObject(SQL_GET_MAX_ID,Integer.class);
        if( id == null) {
            return 1;
        }
        else {
            return id + 1;
        }
    }

    @Override
    public Optional<Therapy> getTherapy(int id) {

        Therapy therapy = jdbcTemplate.queryForObject(SQL_GET_THERAPY_BY_ID, new Object[]{id}, new TherapyMapper());

        return Optional.of(therapy);
    }
}
