package by.emel.anton;

import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.implementation.filedao.FileUserDAO;
import by.emel.anton.terminalprog.TerminalProgram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootApplication
@Transactional
public class Runner implements CommandLineRunner {

    /*@Autowired
    private TerminalProgram terminalProgram;*/
    @Autowired
    FileUserDAO fileUserDAO;

    public static void main(String[] args) {

        SpringApplication.run(Runner.class, args);
    }

    @Override
    public void run(String... args) {

        /*terminalProgram.startProgram();*/
        Patient patient = new Patient();
        patient.setBirthday(LocalDate.of(2000,10,1));
        patient.setName("NaaaaaUPDATE");
        patient.setPassword("Passs");
        patient.setLogin("Ldd");
        try {
            fileUserDAO.updateUser(patient);
        } catch (UserDAOException e) {
            e.printStackTrace();
        }

    }
}
