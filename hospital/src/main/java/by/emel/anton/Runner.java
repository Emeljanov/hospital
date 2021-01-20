package by.emel.anton;

import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.implementation.jdbctemplatedao.JdbcTemplateDoctorDAO;
import by.emel.anton.model.dao.implementation.jdbctemplatedao.JdbcTemplatePatientDAO;
import by.emel.anton.model.dao.implementation.jdbctemplatedao.JdbcTemplateTherapyDAO;
import by.emel.anton.model.dao.interfaces.TherapyDAO;
import by.emel.anton.terminalprog.TerminalProgram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootApplication
@Transactional
public class Runner implements CommandLineRunner  {

    @Autowired
    private TerminalProgram terminalProgram;
   /* @Autowired
    private JdbcTemplateDoctorDAO doctorDAO;
    @Autowired
    JdbcTemplatePatientDAO patientDAO;
    @Autowired
    JdbcTemplateTherapyDAO therapyDAO;*/
    public static void main(String[] args) {

        SpringApplication.run(Runner.class,args);
    }

    @Override
    public void run(String... args)  {

        terminalProgram.startProgram();
        /*try {
          *//* Optional<Patient> patient = patientDAO.getPatientById(3);
           patient.ifPresent(patient1 -> System.out.println(patient1.getTherapies().toString()));*//*
            Optional<Therapy> therapy = therapyDAO.getTherapy(2);
            therapy.ifPresent(th -> System.out.println(th.getPatient().getDoctor().getPatients().toString()));

        } catch (UserDAOException e) {
            e.printStackTrace();
        }*/

    }
}
