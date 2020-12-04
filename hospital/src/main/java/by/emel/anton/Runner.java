package by.emel.anton;

import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.beans.users.UserType;
import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.beans.users.doctors.GeneralDoctor;
import by.emel.anton.model.beans.users.patients.OrdinaryPatient;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.implementation.FileDoctorDAO;
import by.emel.anton.model.dao.implementation.FileUserDAO;
import by.emel.anton.service.StringToList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Runner {
    public static void main(String[] args) throws UserDAOException {
     /*   GeneralDoctor generalDoctor = new GeneralDoctor(1,"FirstDoctor", "passw", "NameDoctor", LocalDate.of(1990, 9,9));
        generalDoctor.setPatientId(1);
        generalDoctor.setPatientId(2);
        generalDoctor.setPatientId(542);
*/
        FileDoctorDAO fdd = new FileDoctorDAO();
        fdd.getDoctor("","");


    }
}
