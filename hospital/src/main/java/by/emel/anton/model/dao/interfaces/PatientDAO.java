package by.emel.anton.model.dao.interfaces;

import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDaoUncheckedException;

import java.util.Optional;

/**
 * @author Anton Yemialynau
 */

public interface PatientDAO {
    Optional<Patient> getPatient(String login, String password) throws UserDaoUncheckedException;

    Optional<Patient> getPatientById(int id) throws UserDaoUncheckedException;
}
