package by.emel.anton;

import by.emel.anton.terminalprog.TerminalProgram;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Runner {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(Runner.class,args);
        TerminalProgram terminalProgram = context.getBean(TerminalProgram.class);
        terminalProgram.startProgram();

    }

}
