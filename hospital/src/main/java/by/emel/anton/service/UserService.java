package by.emel.anton.service;

import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.TherapyDAOException;
import by.emel.anton.model.dao.exceptions.UserDAOException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public interface UserService {

    void createUser(User user, String login, String password, String name, LocalDate birthday,boolean isSave) throws UserDAOException;

    void saveUser(User user) throws UserDAOException;

    void saveTherapy(Therapy therapy) throws TherapyDAOException;

    Optional<Doctor> getDoctor(String login, String password) throws UserDAOException;

    Optional<Patient> getPatient(String login, String password) throws UserDAOException;

    Optional<Patient> getPatientById(int id) throws UserDAOException;

    Optional<Therapy> getTherapy(int id) throws TherapyDAOException;

    void updateUser(User user) throws UserDAOException;

    void addTherapy(Doctor doctor, Patient patient, String description, LocalDate endDate) throws TherapyDAOException, UserDAOException;

    void addPatientToDoctor(Doctor doctor, int patientId) throws UserDAOException;

}
