package by.emel.anton.model.dao.interfaces;

import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.dao.exceptions.UserDAOException;

import java.util.Optional;

public interface DoctorDAO {

    Optional<Doctor> getDoctor(String login, String password) throws UserDAOException;

    Optional<Doctor> getDoctorById(int id) throws UserDAOException;

}
