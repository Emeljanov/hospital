package by.emel.anton;

import by.emel.anton.model.dao.exceptions.TherapyDAOException;
import by.emel.anton.model.dao.implementation.hibernatedao.HibernateTherapyDAO;
import by.emel.anton.model.dao.interfaces.TherapyDAO;
import by.emel.anton.terminalprog.TerminalProgram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Runner implements CommandLineRunner  {

  /*  @Autowired
    private TerminalProgram terminalProgram;

    public static void main(String[] args) {

        SpringApplication.run(Runner.class,args);
    }

    @Override
    public void run(String... args)  {

        terminalProgram.startProgram();

    }*/
    @Autowired
    @Qualifier("TherapyHibernate")
    private HibernateTherapyDAO hibernateTherapyDAO;

    public static void main(String[] args) {
        SpringApplication.run(Runner.class,args);
    }

    @Override
    public void run(String... args) {
//       hibernateTherapyDAO.getOrdTh(1);

    }
}
