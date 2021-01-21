package by.emel.anton.service.hibrenateservice;

import by.emel.anton.model.dao.interfaces.DoctorDAO;
import by.emel.anton.model.dao.interfaces.PatientDAO;
import by.emel.anton.model.dao.interfaces.TherapyDAO;
import by.emel.anton.model.dao.interfaces.UserDAO;
import by.emel.anton.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("HibernateService")
public class HibernateUserServiceImp extends UserServiceImp {
    @Autowired
    public HibernateUserServiceImp(
            @Qualifier("HibernateUserDAO") UserDAO userDAO,
            @Qualifier("HibernatePatientDAO") PatientDAO patientDAO,
            @Qualifier("HibernateDoctorDAO") DoctorDAO doctorDAO,
            @Qualifier("HibernateTherapyDAO") TherapyDAO therapyDAO) {
        super(userDAO, patientDAO, doctorDAO, therapyDAO);
    }
}
