package by.emel.anton.service;

import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public interface UserService {

    void createUser(User user, String login, String password, String name, LocalDate birthday,boolean isSave) throws IOException;

    void saveUser(User user) throws IOException;

    void saveTherapy(Therapy therapy) throws IOException;

    Optional<Doctor> getDoctor(String login, String password) throws UserDAOException, IOException;

    Optional<Patient> getPatient(String login, String password) throws UserDAOException, IOException;

    Optional<Patient> getPatientById(int id) throws UserDAOException, IOException;

    Optional<Therapy> getTherapy(int id) throws IOException;

    void updateUser(User user) throws IOException;

    void addTherapy(Doctor doctor, Patient patient, String description, LocalDate endDate) throws IOException;

    void addPatientToDoctor(Doctor doctor, int patientId) throws UserDAOException, IOException;

}
