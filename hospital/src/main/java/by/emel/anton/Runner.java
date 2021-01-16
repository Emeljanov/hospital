package by.emel.anton;

import by.emel.anton.model.beans.therapy.OrdinaryTherapy;
import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.dao.exceptions.TherapyDAOException;
import by.emel.anton.model.dao.implementation.hibernatedao.HibernateTherapyDAO;
import by.emel.anton.model.dao.interfaces.TherapyDAO;
import by.emel.anton.terminalprog.TerminalProgram;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
//@EntityScan(basePackages = {"by/emel/anton/model/beans"})
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
      Therapy therapy = new OrdinaryTherapy();
      therapy.setDescription("disc4");
      therapy.setStartDate(LocalDate.of(2000,01,10));
      therapy.setEndDate((LocalDate.of(2020,01,10)));
      therapy.setIdPatient(2);
//      therapy.setId(1);
      try {
        hibernateTherapyDAO.saveTherapy(therapy);
      } catch (TherapyDAOException e) {
        e.printStackTrace();
      }
    }
}
