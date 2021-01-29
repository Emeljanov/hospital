package by.emel.anton.model.dao.interfaces;

import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.dao.exceptions.UserDaoUncheckedException;

import java.util.Optional;

public interface DoctorDAO {

    Optional<Doctor> getDoctor(String login, String password) throws UserDaoUncheckedException;

    Optional<Doctor> getDoctorById(int id) throws UserDaoUncheckedException;

}
