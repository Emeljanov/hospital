package by.emel.anton;

import by.emel.anton.model.dao.implementation.jdbctemplatedao.JdbcTemplateUserDao;
import by.emel.anton.terminalprog.TerminalProgram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Runner implements CommandLineRunner  {

  /*  @Autowired
    private TerminalProgram terminalProgram;*/

    @Autowired
    private JdbcTemplateUserDao jdbcTemplateUserDao;

    public static void main(String[] args) {

        SpringApplication.run(Runner.class,args);

    }

    @Override
    public void run(String... args) throws Exception {

//        terminalProgram.startProgram();
//        System.out.println(jdbcTemplateUserDao.getStr());


    }
}
