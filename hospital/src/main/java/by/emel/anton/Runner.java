package by.emel.anton;

import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.TherapyDAOException;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.implementation.filedao.FileService;
import by.emel.anton.model.dao.implementation.filedao.FileUserDAO;
import by.emel.anton.model.dao.implementation.filedao.FileTherapyDAO;
import by.emel.anton.terminalprog.TerminalProgram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@Transactional
public class Runner implements CommandLineRunner {

    @Autowired
    private TerminalProgram terminalProgram;


    public static void main(String[] args) {

        SpringApplication.run(Runner.class, args);
    }

    @Override
    public void run(String... args) {

        terminalProgram.startProgram();

    }
}
