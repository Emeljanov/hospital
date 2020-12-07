package by.emel.anton.model.dao.interfaces;

import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDAOException;

/** @author Anton Yemialynau  */

public interface PatientDAO {
    Patient getPatient(String login, String password) throws UserDAOException;

    Patient getPatientById(int id) throws UserDAOException ;
}
