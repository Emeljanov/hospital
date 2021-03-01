package by.emel.anton;

import by.emel.anton.model.entity.users.User;
import by.emel.anton.terminalprog.TerminalProgram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

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

       /* terminalProgram.startProgram();*/
    }
}
