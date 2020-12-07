package by.emel.anton.model.dao.interfaces;

import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.dao.exceptions.UserDAOException;

public interface DoctorDAO {

    Doctor getDoctor(String login, String password) throws UserDAOException;


}
