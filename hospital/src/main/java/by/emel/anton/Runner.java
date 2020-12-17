package by.emel.anton;

import by.emel.anton.terminalprog.TerminalProgram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Runner implements CommandLineRunner {

    @Autowired
    TerminalProgram terminalProgram;

    public static void main(String[] args) {

     /*   ConfigurableApplicationContext context = SpringApplication.run(Runner.class,args);
        TerminalProgram terminalProgram = context.getBean(TerminalProgram.class);
        terminalProgram.startProgram();*/

        SpringApplication.run(Runner.class,args);

    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("fdsf");

    }
}
