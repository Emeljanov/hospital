package by.emel.anton.service;

import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.TherapyDaoUncheckedException;
import by.emel.anton.model.dao.exceptions.UserDaoUncheckedException;

import java.time.LocalDate;
import java.util.Optional;

public interface UserService {

    void createUser(User user, String login, String password, String name, LocalDate birthday, boolean isSave) throws UserDaoUncheckedException;

    void saveUser(User user) throws UserDaoUncheckedException;

    void saveTherapy(Therapy therapy) throws TherapyDaoUncheckedException;

    Optional<Doctor> getDoctor(String login, String password) throws UserDaoUncheckedException;

    Optional<Doctor> getDoctorById(int id) throws UserDaoUncheckedException;

    Optional<Patient> getPatient(String login, String password) throws UserDaoUncheckedException;

    Optional<Patient> getPatientById(int id) throws UserDaoUncheckedException;

    Optional<Therapy> getTherapy(int id) throws TherapyDaoUncheckedException, UserDaoUncheckedException;

    void updateUser(User user) throws UserDaoUncheckedException;

    void addTherapy(Patient patient, String description, LocalDate endDate) throws TherapyDaoUncheckedException, UserDaoUncheckedException;

    void addPatientToDoctor(Doctor doctor, int patientId) throws UserDaoUncheckedException;

}
