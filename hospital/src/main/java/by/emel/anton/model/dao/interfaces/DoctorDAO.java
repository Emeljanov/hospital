package by.emel.anton.model.dao.interfaces;

import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDAOException;

import java.util.Optional;

public interface DoctorDAO {

    Optional<Doctor> getDoctor(String login, String password) throws UserDAOException;

//    void setDoctorIdToPatient(Doctor doctor, Patient patient);


}
