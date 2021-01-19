package by.emel.anton.model.dao.implementation.jdbctemplatedao;

import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.dao.implementation.jdbctemplatedao.rowmappers.TherapyMapper;
import by.emel.anton.model.dao.interfaces.TherapyDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("TherapyJdbcTemplate")
public class JdbcTemplateTherapyDAO implements TherapyDAO {
    private static final String SQL_SAVE_THERAPY =
            "insert into therapy (description, start_date, end_date,patient_id) values (?, ?,?, ?)";
    private static final String SQL_GET_THERAPY_BY_ID = "select * from therapy where id = ?";
    private static final String SQL_GET_MAX_ID = "select max(id) from therapy";

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
        int patientId = therapy.getPatient().getId();

        jdbcTemplate.update(SQL_SAVE_THERAPY,desc,startDate,endDate,patientId);
    }

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

        int patientId = 1;
        int doctorId = 1;
        List<Integer> patTrpsInt = new ArrayList<>();
        List<Integer> doctPatsInt = new ArrayList<>();


        Therapy therapy = jdbcTemplate.queryForObject(SQL_GET_THERAPY_BY_ID, new Object[]{id}, new TherapyMapper());

        return Optional.ofNullable(therapy);
    }
}
