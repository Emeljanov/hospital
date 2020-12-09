package by.emel.anton.service;

import by.emel.anton.model.beans.therapy.OrdinaryTherapy;
import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.interfaces.DoctorDAO;
import by.emel.anton.model.dao.interfaces.PatientDAO;
import by.emel.anton.model.dao.interfaces.TherapyDAO;
import by.emel.anton.model.dao.interfaces.UserDAO;

import java.time.LocalDate;
import java.util.Optional;

public class UserService {

    private final UserDAO userDAO;
    private final PatientDAO patientDAO;
    private final DoctorDAO doctorDAO;
    private final TherapyDAO therapyDAO;


    public UserService(UserDAO userDAO, PatientDAO patientDAO, DoctorDAO doctorDAO, TherapyDAO therapyDAO) {
        this.userDAO = userDAO;
        this.patientDAO = patientDAO;
        this.doctorDAO = doctorDAO;
        this.therapyDAO = therapyDAO;
    }

    public void setUserData(User user, String login, String password, String name, LocalDate birthday) {
        int id = userDAO.getNextId(user);
        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setName(name);
        user.setBirthday(birthday);
    }

    public void saveUser(User user) {
        userDAO.saveUser(user);
    }
    public void saveTherapy(Therapy therapy) {
        therapyDAO.saveTherapy(therapy);
    }

    public Optional<Doctor> getDoctor(String login, String password) throws UserDAOException {
        return doctorDAO.getDoctor(login,password);
    }
    public Optional<Patient> getPatient(String login, String password) throws UserDAOException {
        return patientDAO.getPatient(login,password);
    }
    public Optional<Patient> getPatientById(int id) throws UserDAOException {
        return patientDAO.getPatientById(id);
    }
    public Optional<Therapy> getTherapy(int id) {
        return therapyDAO.getTherapy(id);
    }
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }
    public void addTherapy(Doctor doctor, Patient patient, String description, LocalDate endDate) {

        int therapyId = therapyDAO.getNextID();
        Therapy therapy = new OrdinaryTherapy(therapyId,description,LocalDate.now(),endDate);
        doctor.setTherapy(patient,therapy);
        updateUser(patient);
        saveTherapy(therapy);

    }
    public void addPatientToDoctor(Doctor doctor, int patientId) throws UserDAOException {
        Optional<Patient> patient = getPatientById(patientId);
        patient.ifPresent(pat -> {
            doctor.setPatientId(patientId);
            pat.setDoctorId(doctor.getId());
            updateUser(doctor);
            updateUser(pat);
        });
    }

}
