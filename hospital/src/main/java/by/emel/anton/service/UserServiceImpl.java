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

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final PatientDAO patientDAO;
    private final DoctorDAO doctorDAO;
    private final TherapyDAO therapyDAO;


    public UserServiceImpl(UserDAO userDAO, PatientDAO patientDAO, DoctorDAO doctorDAO, TherapyDAO therapyDAO) {
        this.userDAO = userDAO;
        this.patientDAO = patientDAO;
        this.doctorDAO = doctorDAO;
        this.therapyDAO = therapyDAO;
    }

    @Override
    public void createUser(User user, String login, String password, String name, LocalDate birthday, boolean isSave) throws IOException {
        int id = userDAO.getNextId(user);
        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setName(name);
        user.setBirthday(birthday);
        if(isSave) {
            saveUser(user);
        }
    }

    @Override
    public void saveUser(User user) throws IOException {
        userDAO.saveUser(user);
    }

    @Override
    public void saveTherapy(Therapy therapy) throws IOException {
        therapyDAO.saveTherapy(therapy);
    }

    @Override
    public Optional<Doctor> getDoctor(String login, String password) throws UserDAOException, IOException {
        return doctorDAO.getDoctor(login,password);
    }

    @Override
    public Optional<Patient> getPatient(String login, String password) throws UserDAOException, IOException {
        return patientDAO.getPatient(login,password);
    }

    @Override
    public Optional<Patient> getPatientById(int id) throws UserDAOException, IOException {
        return patientDAO.getPatientById(id);
    }

    @Override
    public Optional<Therapy> getTherapy(int id) throws IOException {
        return therapyDAO.getTherapy(id);
    }

    @Override
    public void updateUser(User user) throws IOException {
        userDAO.updateUser(user);
    }

    @Override
    public void addTherapy(Doctor doctor, Patient patient, String description, LocalDate endDate) throws IOException {

        int therapyId = therapyDAO.getNextID();
        Therapy therapy = new OrdinaryTherapy(therapyId,description,LocalDate.now(),endDate);
        doctor.setTherapy(patient,therapy);
        updateUser(patient);
        saveTherapy(therapy);

    }

    @Override
    public void addPatientToDoctor(Doctor doctor, int patientId) throws UserDAOException, IOException {
        Optional<Patient> patient = getPatientById(patientId);
        patient.ifPresent(pat -> {
            doctor.setPatientId(patientId);
            pat.setDoctorId(doctor.getId());
            try {
                updateUser(doctor);
                updateUser(pat);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

}
