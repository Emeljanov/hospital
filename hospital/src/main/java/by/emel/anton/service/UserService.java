package by.emel.anton.service;

import by.emel.anton.model.entity.therapy.Therapy;
import by.emel.anton.model.entity.users.User;
import by.emel.anton.model.entity.users.doctors.Doctor;
import by.emel.anton.model.entity.users.patients.Patient;

import java.time.LocalDate;
import java.util.Optional;

public interface UserService {

    void createUser(User user, String login, String password, String name, LocalDate birthday, boolean isSave);

    void saveUser(User user);

    void saveTherapy(Therapy therapy);

    Optional<Doctor> getDoctor(String login, String password);

    Optional<Doctor> getDoctorById(int id);

    Optional<Patient> getPatient(String login, String password);

    Optional<Patient> getPatientById(int id);

    Optional<Therapy> getTherapy(int id);

    void updateUser(User user);

    void addTherapy(Patient patient, String description, LocalDate endDate);

    void addPatientToDoctor(Doctor doctor, int patientId);

    Optional<Doctor> getDoctorByLogin(String login);

    Optional<Patient> getPatientByLogin(String login);

    Optional<User> getUserByLogin(String login);

}
