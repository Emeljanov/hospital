package by.emel.anton;

import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.implementation.springdatadao.SpringDataPatientDAO;
import by.emel.anton.model.dao.implementation.springdatadao.SpringDataUserDAO;
import by.emel.anton.model.dao.implementation.springdatadao.intefaces.PatientJpaRepository;
import by.emel.anton.model.dao.implementation.springdatadao.intefaces.UserJpaRepository;
import by.emel.anton.terminalprog.TerminalProgram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
