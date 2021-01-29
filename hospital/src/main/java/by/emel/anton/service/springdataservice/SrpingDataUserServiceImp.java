package by.emel.anton.service.springdataservice;

import by.emel.anton.model.dao.interfaces.DoctorDAO;
import by.emel.anton.model.dao.interfaces.PatientDAO;
import by.emel.anton.model.dao.interfaces.TherapyDAO;
import by.emel.anton.model.dao.interfaces.UserDAO;
import by.emel.anton.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("SpringDataService")
public class SrpingDataUserServiceImp extends UserServiceImp {
    @Autowired
    public SrpingDataUserServiceImp(
            @Qualifier("SpringDataUserDAO")UserDAO userDAO,
            @Qualifier("SpringDataPatientDAO")PatientDAO patientDAO,
            @Qualifier("SpringDataDoctorDAO")DoctorDAO doctorDAO,
            @Qualifier("SpringDataTherapyDAO")TherapyDAO therapyDAO) {
        super(userDAO, patientDAO, doctorDAO, therapyDAO);
    }
}
