package by.emel.anton.service;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.dao.interfaces.DoctorDAO;
import by.emel.anton.model.dao.interfaces.PatientDAO;
import by.emel.anton.model.dao.interfaces.TherapyDAO;
import by.emel.anton.model.dao.interfaces.UserDAO;
import by.emel.anton.model.entity.therapy.Therapy;
import by.emel.anton.model.entity.users.User;
import by.emel.anton.model.entity.users.doctors.Doctor;
import by.emel.anton.model.entity.users.patients.Patient;
import by.emel.anton.service.exception.UserServiceException;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserServiceImp implements UserService {

    private final UserDAO userDAO;
    private final PatientDAO patientDAO;
    private final DoctorDAO doctorDAO;
    private final TherapyDAO therapyDAO;

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
        try {
            userDAO.saveUser(user);
        } catch (RuntimeException e) {
            throw new UserServiceException("Can't save User");
        }
    }

    @Override
    public void saveTherapy(Therapy therapy) {
        try {
            therapyDAO.saveTherapy(therapy);
        } catch (RuntimeException e) {
            throw new UserServiceException("Can't save Therapy");
        }
    }

    @Override
    public Optional<Doctor> getDoctor(String login, String password) {
        try {
            return doctorDAO.getDoctor(login, password);
        } catch (RuntimeException e) {
            throw new UserServiceException("Can't get Doctor by login and password");
        }
    }

    @Override
    public Optional<Doctor> getDoctorById(int id) {
        try {
            return doctorDAO.getDoctorById(id);
        } catch (RuntimeException e) {
            throw new UserServiceException("Can't get Doctor by id");
        }
    }


    @Override
    public Optional<Patient> getPatient(String login, String password) {
        try {
            return patientDAO.getPatient(login, password);
        } catch (RuntimeException e) {
            throw new UserServiceException("Can't get Patient by login and password");
        }
    }

    @Override
    public Optional<Patient> getPatientById(int id) throws UserServiceException {
        try {
            return patientDAO.getPatientById(id);
        } catch (RuntimeException e) {
            throw new UserServiceException("Can't get Patient by id");
        }
    }

    @Override
    public Optional<Therapy> getTherapy(int id) {
        try {
            return therapyDAO.getTherapy(id);
        } catch (RuntimeException e) {
            throw new UserServiceException("Can't get Therapy");
        }
    }

    @Override
    public void updateUser(User user) {
        try {
            userDAO.updateUser(user);
        } catch (RuntimeException e) {
            throw new UserServiceException("Can't update User");
        }
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
    public void addPatientToDoctor(Doctor doctor, int patientId) throws UserServiceException {

        Patient patient = getPatientById(patientId)
                .orElseThrow(() -> new UserServiceException(Constants.EXCEPTION_NO_ID));
        patient.setDoctor(doctor);
        doctor.addPatient(patient);
        updateUser(patient);
    }

    @Override
    public Optional<Doctor> getDoctorByLogin(String login) {
        try {
            return doctorDAO.getDoctorByLogin(login);
        } catch (RuntimeException e) {
            throw new UserServiceException("Can't get Doctor by login");
        }
    }

    @Override
    public Optional<Patient> getPatientByLogin(String login) {
        try {
            return patientDAO.getPatientByLogin(login);
        } catch (RuntimeException e) {
            throw new UserServiceException("Can't get Patient by login");
        }
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        try {
            return userDAO.getSimpleUserByLogin(login);
        } catch (RuntimeException e) {
            throw new UserServiceException("Can't get User by login");
        }
    }

}
