package by.emel.anton.model.dao.interfaces;

import by.emel.anton.model.entity.users.patients.Patient;

import java.util.Optional;

/**
 * @author Anton Yemialynau
 */

public interface PatientDAO {
    Optional<Patient> getPatient(String login, String password);

    Optional<Patient> getPatientById(int id);
}
