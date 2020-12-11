package by.emel.anton;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.beans.users.doctors.GeneralDoctor;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.implementation.*;
import by.emel.anton.model.dao.interfaces.DoctorDAO;
import by.emel.anton.model.dao.interfaces.PatientDAO;
import by.emel.anton.model.dao.interfaces.TherapyDAO;
import by.emel.anton.model.dao.interfaces.UserDAO;
import by.emel.anton.service.UserService;
import by.emel.anton.service.UserServiceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

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
