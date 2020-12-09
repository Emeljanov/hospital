package by.emel.anton.model.dao.interfaces;

import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDAOException;

import java.util.Optional;

/** @author Anton Yemialynau  */

public interface PatientDAO {
    Optional<Patient> getPatient(String login, String password) throws UserDAOException;

    Optional<Patient> getPatientById(int id) throws UserDAOException ;
}
