package by.emel.anton.service.hibrenateservice;

import by.emel.anton.model.dao.interfaces.DoctorDAO;
import by.emel.anton.model.dao.interfaces.PatientDAO;
import by.emel.anton.model.dao.interfaces.TherapyDAO;
import by.emel.anton.model.dao.interfaces.UserDAO;
import by.emel.anton.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("FromHibernate")
public class UserServiceHibernateImp extends UserServiceImp {
    @Autowired
    public UserServiceHibernateImp(
            @Qualifier("UserHibernate") UserDAO userDAO,
            @Qualifier("PatientHibernate") PatientDAO patientDAO,
            @Qualifier("DoctorHibernate") DoctorDAO doctorDAO,
            @Qualifier("TherapyHibernate") TherapyDAO therapyDAO) {
        super(userDAO, patientDAO, doctorDAO, therapyDAO);
    }
}
