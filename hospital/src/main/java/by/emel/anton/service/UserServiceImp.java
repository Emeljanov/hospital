package by.emel.anton.service;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.beans.therapy.OrdinaryTherapy;
import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.TherapyDAOException;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.interfaces.DoctorDAO;
import by.emel.anton.model.dao.interfaces.PatientDAO;
import by.emel.anton.model.dao.interfaces.TherapyDAO;
import by.emel.anton.model.dao.interfaces.UserDAO;

import java.time.LocalDate;
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
    public void createUser(User user, String login, String password, String name, LocalDate birthday, boolean isSave) throws UserDAOException {
        user.setLogin(login);
        user.setPassword(password);
        user.setName(name);
        user.setBirthday(birthday);
        if(isSave) {
            saveUser(user);
        }
    }

    @Override
    public void saveUser(User user) throws UserDAOException {
        userDAO.saveUser(user);
    }

    @Override
    public void saveTherapy(Therapy therapy) throws TherapyDAOException {
        therapyDAO.saveTherapy(therapy);
    }

    @Override
    public Optional<Doctor> getDoctor(String login, String password) throws UserDAOException {
        return doctorDAO.getDoctor(login,password);
    }

    @Override
    public Optional<Patient> getPatient(String login, String password) throws UserDAOException{
        return patientDAO.getPatient(login,password);
    }

    @Override
    public Optional<Patient> getPatientById(int id) throws UserDAOException{
        return patientDAO.getPatientById(id);
    }

    @Override
    public Optional<Therapy> getTherapy(int id) throws TherapyDAOException, UserDAOException {
        return therapyDAO.getTherapy(id);
    }

    @Override
    public void updateUser(User user) throws UserDAOException {
        userDAO.updateUser(user);
    }

    @Override
    public void addTherapy(Patient patient, String description, LocalDate endDate) throws UserDAOException, TherapyDAOException {

        Therapy therapy = new OrdinaryTherapy();
        therapy.setDescription(description);
        therapy.setStartDate(LocalDate.now());
        therapy.setEndDate(endDate);
        therapy.setPatient(patient);
        patient.addTherapy(therapy);
        saveTherapy(therapy);

    }

    @Override
    public void addPatientToDoctor(Doctor doctor,int patientId) throws UserDAOException {

        Patient patient = getPatientById(patientId)
                .orElseThrow(() -> new UserDAOException(Constants.EXCEPTION_NO_ID));
        patient.setDoctor(doctor);
        doctor.addPatient(patient);
        updateUser(patient);

    }

}
