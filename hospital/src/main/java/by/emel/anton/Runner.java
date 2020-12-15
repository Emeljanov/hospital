package by.emel.anton;

import by.emel.anton.model.dao.implementation.FileDoctorDAO;
import by.emel.anton.model.dao.implementation.FilePatientDAO;
import by.emel.anton.model.dao.implementation.FileTherapyDAO;
import by.emel.anton.model.dao.implementation.FileUserDAO;
import by.emel.anton.model.dao.interfaces.DoctorDAO;
import by.emel.anton.model.dao.interfaces.PatientDAO;
import by.emel.anton.model.dao.interfaces.TherapyDAO;
import by.emel.anton.model.dao.interfaces.UserDAO;
import by.emel.anton.service.UserService;
import by.emel.anton.service.UserServiceImpl;
import by.emel.anton.terminalprog.TerminalProgram;

public class Runner {

    public static void main(String[] args) {

        UserDAO userDAO = new FileUserDAO();
        PatientDAO patientDAO = new FilePatientDAO();
        DoctorDAO doctorDAO = new FileDoctorDAO();
        TherapyDAO therapyDAO = new FileTherapyDAO();
        UserService userService = new UserServiceImpl(userDAO,patientDAO,doctorDAO,therapyDAO);
        TerminalProgram terminalProgram = new TerminalProgram(userService);
        terminalProgram.startProgram();

    }

}
