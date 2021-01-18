package by.emel.anton;

import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.implementation.hibernatedao.HibernateTherapyDAO;
import by.emel.anton.model.dao.interfaces.DoctorDAO;
import by.emel.anton.model.dao.interfaces.PatientDAO;
import by.emel.anton.model.dao.interfaces.UserDAO;
import by.emel.anton.terminalprog.TerminalProgram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;

@SpringBootApplication
@Transactional
public class Runner implements CommandLineRunner  {

    @Autowired
    private TerminalProgram terminalProgram;

    public static void main(String[] args) {

        SpringApplication.run(Runner.class,args);
    }

    @Override
    public void run(String... args)  {

        terminalProgram.startProgram();

    }
   /* @Autowired
    EntityManager entityManager;

    @Autowired
    @Qualifier("TherapyHibernate")
    private HibernateTherapyDAO hibernateTherapyDAO;
    @Autowired
    @Qualifier("UserHibernate")
    private UserDAO hibUserDAO;
    @Autowired
    @Qualifier("PatientHibernate")
    private PatientDAO patientDAO;
    @Autowired
    @Qualifier("DoctorHibernate")
    private DoctorDAO doctorDAO;


    public static void main(String[] args) {
        SpringApplication.run(Runner.class,args);
    }

    @Override
    public void run(String... args) {
       *//* Patient patient = new Patient();
        patient.setBirthday(LocalDate.of(1900,01,01));
        patient.setName("NamPat");
        patient.setLogin("PatLog1");
        patient.setPassword("123");
        try {
            hibUserDAO.saveUser(patient);
        } catch (UserDAOException e) {
            e.printStackTrace();
        }*//*
//        System.out.println(entityManager.find(Doctor.class,1));
        try {
            System.out.println(patientDAO.getPatient("PatLog1","123"));
        } catch (UserDAOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Run run run");


    }*/


}
