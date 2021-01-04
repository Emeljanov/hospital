package by.emel.anton;

import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.beans.users.doctors.GeneralDoctor;
import by.emel.anton.model.beans.users.patients.OrdinaryPatient;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.implementation.jdbctemplatedao.JdbcTemplateUserDao;
import by.emel.anton.terminalprog.TerminalProgram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class Runner implements CommandLineRunner  {

/*    @Autowired
    private TerminalProgram terminalProgram;*/

    @Autowired
    private JdbcTemplateUserDao jdbcTemplateUserDao;

    public static void main(String[] args) {

        SpringApplication.run(Runner.class,args);

    }

    @Override
    public void run(String... args) throws Exception {

       /* terminalProgram.startProgram();*/

//        System.out.println(jdbcTemplateUserDao.isLoginExist("ant"));
        Patient patient = new OrdinaryPatient();
        patient.setLogin("loginP");
        patient.setPassword("213");
        patient.setName("new UPDddddPat");
        patient.setDoctorId(6);
        patient.setBirthday(LocalDate.of(1913,01,01));
        jdbcTemplateUserDao.updateUser(patient);


    }
}
