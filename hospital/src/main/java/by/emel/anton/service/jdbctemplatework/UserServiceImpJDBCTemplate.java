package by.emel.anton.service.jdbctemplatework;

import by.emel.anton.model.dao.interfaces.DoctorDAO;
import by.emel.anton.model.dao.interfaces.PatientDAO;
import by.emel.anton.model.dao.interfaces.TherapyDAO;
import by.emel.anton.model.dao.interfaces.UserDAO;
import by.emel.anton.service.filework.UserServiceFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("FromJDBCTemplate")
public class UserServiceImpJDBCTemplate extends UserServiceFile {

    @Autowired
    public UserServiceImpJDBCTemplate( /*@Qualifier("UserJdbcTemplate")*/ UserDAO userDAO,
                                       /*@Qualifier("PatientJdbcTemplate")*/PatientDAO patientDAO,
                                       /*@Qualifier("DoctorJdbcTemplate")*/DoctorDAO doctorDAO,
                                       /*@Qualifier("TherapyJdbcTemplate")*/TherapyDAO therapyDAO) {
        super(userDAO, patientDAO, doctorDAO, therapyDAO);
    }
}