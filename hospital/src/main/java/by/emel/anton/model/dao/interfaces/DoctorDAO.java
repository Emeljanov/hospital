package by.emel.anton.model.dao.interfaces;

import by.emel.anton.model.beans.users.doctors.Doctor;

import java.util.Optional;

public interface DoctorDAO {

    Optional<Doctor> getDoctor(String login, String password);

    Optional<Doctor> getDoctorById(int id);

}
