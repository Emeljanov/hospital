package by.emel.anton.service;

import by.emel.anton.model.beans.therapy.OrdinaryTherapy;
import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDAOException;

import java.time.LocalDate;
import java.util.Optional;

public interface UserService {

    public void setUserData(User user, String login, String password, String name, LocalDate birthday);

    public void saveUser(User user);

    public void saveTherapy(Therapy therapy);

    public Optional<Doctor> getDoctor(String login, String password) throws UserDAOException;

    public Optional<Patient> getPatient(String login, String password) throws UserDAOException ;

    public Optional<Patient> getPatientById(int id) throws UserDAOException;

    public Optional<Therapy> getTherapy(int id);

    public void updateUser(User user);

    public void addTherapy(Doctor doctor, Patient patient, String description, LocalDate endDate);

    public void addPatientToDoctor(Doctor doctor, int patientId) throws UserDAOException ;

}
