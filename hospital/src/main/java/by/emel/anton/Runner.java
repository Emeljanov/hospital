package by.emel.anton;

import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.implementation.springdatadao.SpringDataPatientDAO;
import by.emel.anton.model.dao.implementation.springdatadao.SpringDataUserDAO;
import by.emel.anton.model.dao.implementation.springdatadao.intefaces.PatientJpaRepository;
import by.emel.anton.model.dao.implementation.springdatadao.intefaces.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootApplication
@Transactional
public class Runner implements CommandLineRunner {
/*
    @Autowired
    UserJpaRepository userJpaRepository;*/
    @Autowired
    SpringDataUserDAO springDataUserDAO;
    @Autowired
    SpringDataPatientDAO springDataPatientDAO;

  /*  @Autowired
    private TerminalProgram terminalProgram;
*/

    public static void main(String[] args) {

        SpringApplication.run(Runner.class, args);
    }

    @Override
    public void run(String... args) {

        Optional<Patient> patient = springDataPatientDAO.getPatient("Patient11","111");
        System.out.println(patient);

        /*Patient pat = patient.get();
        pat.setName("NameJPA");
        springDataUserDAO.updateUser(pat);
*/
    }
}
