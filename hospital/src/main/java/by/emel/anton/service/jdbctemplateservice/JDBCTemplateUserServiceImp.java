package by.emel.anton.service.jdbctemplateservice;

import by.emel.anton.model.dao.interfaces.DoctorDAO;
import by.emel.anton.model.dao.interfaces.PatientDAO;
import by.emel.anton.model.dao.interfaces.TherapyDAO;
import by.emel.anton.model.dao.interfaces.UserDAO;
import by.emel.anton.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("JdbcTemplateService")
public class JDBCTemplateUserServiceImp extends UserServiceImp {

    @Autowired
    public JDBCTemplateUserServiceImp(@Qualifier("JdbcTemplateUserDAO")UserDAO userDAO,
                                      @Qualifier("JdbcTemplatePatientDAO")PatientDAO patientDAO,
                                      @Qualifier("JdbcTemplateDoctorDAO")DoctorDAO doctorDAO,
                                      @Qualifier("JdbcTemplateTherapyDAO")TherapyDAO therapyDAO) {
        super(userDAO, patientDAO, doctorDAO, therapyDAO);
    }

}
