package by.emel.anton;

import by.emel.anton.model.beans.therapy.OrdinaryTherapy;
import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.implementation.FileDoctorDAO;
import by.emel.anton.model.dao.implementation.FilePatientDAO;
import by.emel.anton.model.dao.implementation.FileTherapyDAO;
import by.emel.anton.model.dao.implementation.FileUserDAO;
import by.emel.anton.model.dao.interfaces.DoctorDAO;
import by.emel.anton.model.dao.interfaces.PatientDAO;
import by.emel.anton.model.dao.interfaces.TherapyDAO;
import by.emel.anton.model.dao.interfaces.UserDAO;

import java.time.LocalDate;

public class Runner {
    public static void main(String[] args) throws UserDAOException {

        UserDAO userDAO = new FileUserDAO();
        PatientDAO patientDAO = new FilePatientDAO();
        DoctorDAO doctorDAO = new FileDoctorDAO();
        TherapyDAO therapyDAO = new FileTherapyDAO();

        TerminalProgramm terminalProgramm = new TerminalProgramm(userDAO,patientDAO,doctorDAO,therapyDAO);
        terminalProgramm.startProgramm();

    }
}
