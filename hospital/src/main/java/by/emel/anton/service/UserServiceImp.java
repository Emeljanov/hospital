package by.emel.anton.service;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.entity.therapy.Therapy;
import by.emel.anton.model.entity.users.User;
import by.emel.anton.model.entity.users.doctors.Doctor;
import by.emel.anton.model.entity.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDaoException;
import by.emel.anton.model.dao.interfaces.DoctorDAO;
import by.emel.anton.model.dao.interfaces.PatientDAO;
import by.emel.anton.model.dao.interfaces.TherapyDAO;
import by.emel.anton.model.dao.interfaces.UserDAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserServiceImp implements UserService {

    private final UserDAO userDAO;
    private final PatientDAO patientDAO;
    private final DoctorDAO doctorDAO;
    private final TherapyDAO therapyDAO;

    public UserServiceImp(
            UserDAO userDAO,
            PatientDAO patientDAO,
            DoctorDAO doctorDAO,
            TherapyDAO therapyDAO) {

        this.userDAO = userDAO;
        this.patientDAO = patientDAO;
        this.doctorDAO = doctorDAO;
        this.therapyDAO = therapyDAO;
    }

    @Override
    public void createUser(User user, String login, String password, String name, LocalDate birthday, boolean isSave) {
        user.setLogin(login);
        user.setPassword(password);
        user.setName(name);
        user.setBirthday(birthday);
        if (isSave) {
            saveUser(user);
        }
    }

    @Override
    public void saveUser(User user) {
        userDAO.saveUser(user);
    }

    @Override
    public void saveTherapy(Therapy therapy) {
        therapyDAO.saveTherapy(therapy);
    }

    @Override
    public Optional<Doctor> getDoctor(String login, String password) {
        return doctorDAO.getDoctor(login, password);
    }

    @Override
    public Optional<Doctor> getDoctorById(int id) {
        return doctorDAO.getDoctorById(id);
    }

    @Override
    public Optional<Patient> getPatient(String login, String password) {
        return patientDAO.getPatient(login, password);
    }

    @Override
    public Optional<Patient> getPatientById(int id) {
        return patientDAO.getPatientById(id);
    }

    @Override
    public Optional<Therapy> getTherapy(int id) {
        return therapyDAO.getTherapy(id);
    }

    @Override
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    @Override
    public void addTherapy(Patient patient, String description, LocalDate endDate) {

        Therapy therapy = new Therapy();
        therapy.setDescription(description);
        therapy.setStartDate(LocalDate.now());
        therapy.setEndDate(endDate);
        therapy.setPatient(patient);
        Optional<List<Therapy>> therapies = Optional.ofNullable(patient.getTherapies());
        therapies.ifPresentOrElse(t -> t.add(therapy),
                () -> {
                    List<Therapy> t = new ArrayList<>();
                    t.add(therapy);
                    patient.setTherapies(t);
                });
        saveTherapy(therapy);
    }

    @Override
    public void addPatientToDoctor(Doctor doctor, int patientId) {

        Patient patient = getPatientById(patientId)
                .orElseThrow(() -> new UserDaoException(Constants.EXCEPTION_NO_ID));
        patient.setDoctor(doctor);
        doctor.addPatient(patient);
        updateUser(patient);
    }

    @Override
    public Optional<Doctor> getDoctorByLogin(String login) {
        return doctorDAO.getDoctorByLogin(login);
    }

    @Override
    public Optional<Patient> getPatientByLogin(String login) {
        return patientDAO.getPatientByLogin(login);
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        return userDAO.getSimpleUserByLogin(login);
    }

}
