package by.emel.anton.model.dao.implementation.filedao;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.entity.therapy.Therapy;
import by.emel.anton.model.entity.users.User;
import by.emel.anton.model.entity.users.doctors.Doctor;
import by.emel.anton.model.entity.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.TherapyDaoException;
import by.emel.anton.model.dao.exceptions.UserDaoException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FileServiceDAO {

    public boolean isLoginPasswordCorrect(String data, String login, String password) {
        String[] userData = data.split(Constants.SEPARATOR);
        String loginData = userData[1];
        String passwordData = userData[2];

        return loginData.equals(login) && passwordData.equals(password);
    }

    public boolean isIdCorrect(String data, int id) {
        String[] userData = data.split(Constants.SEPARATOR);
        int idData = Integer.parseInt(userData[0]);

        return id == idData;
    }

    public int getNextLineId(Path filePath) {

        try {
            List<String> fileData = Files.readAllLines(filePath);
            String lineMaxID = fileData.stream().max(new LineIdComaparator()).orElse("0/");

            return Integer.parseInt(lineMaxID.split(Constants.SEPARATOR)[0]) + 1;

        } catch (IOException e) {
            throw new UserDaoException("ERROR getNextLineID");
        }
    }

    public Map<Integer, List<Integer>> getDoctorPatientsIdMap() {
        try {
            return Files.readAllLines(Paths.get(Constants.FILE_PATH_PATIENTS))
                    .stream()
                    .collect(Collectors.groupingBy(line -> Integer.valueOf(line.split(Constants.SEPARATOR)[1]),
                            Collectors.mapping(line -> Integer.valueOf(line.split(Constants.SEPARATOR)[0]), Collectors.toList())));

        } catch (IOException e) {
            throw new UserDaoException("ERROR get Patient id list");
        }
    }

    public void addTherapiesToPatient(Patient patient) {

        try {
            List<String> listLineThe = Files
                    .readAllLines(Paths.get(Constants.FILE_PATH_THERAPIES))
                    .stream()
                    .filter(line -> Integer.parseInt(line.split(Constants.SEPARATOR)[4]) == patient.getId())
                    .collect(Collectors.toList());

            List<Therapy> therapies = listLineThe
                    .stream()
                    .map(this::createTherapyFromLine)
                    .collect(Collectors.toList());

            therapies.forEach(t -> t.setPatient(patient));

            patient.setTherapies(therapies);

        } catch (IOException e) {
            throw new TherapyDaoException();
        }
    }

    public void addPatientsToDoctor(Doctor doctor) {

        try {
            List<String> dataFile = Files.readAllLines(Paths.get(Constants.FILE_PATH_USERS));
            List<Patient> patients = new ArrayList<>();

            List<Integer> patientIds = Optional.ofNullable(doctor)
                    .map(User::getId)
                    .map(getDoctorPatientsIdMap()::get)
                    .orElse(Collections.emptyList());

            patientIds.forEach(id -> addPatientsToListFromFile(dataFile, id, patients, doctor));
            doctor.setPatients(patients);
        } catch (IOException e) {
            throw new UserDaoException();
        }

    }

    private void addPatientsToListFromFile(List<String> datafile, int id, List<Patient> patients, Doctor doctor) {
        datafile.forEach(data -> {
            String[] patientData = data.split(Constants.SEPARATOR);
            if (patientData[0].equals(String.valueOf(id))) {
                patients.add(createPatient(patientData, doctor));
            }
        });
    }

    public Therapy createTherapyFromLine(String line) {
        String[] therapyData = line.split(Constants.SEPARATOR);
        int id = Integer.parseInt(therapyData[0]);
        String description = therapyData[1];
        LocalDate startDate = LocalDate.parse(therapyData[2]);
        LocalDate endDate = LocalDate.parse(therapyData[3]);
        int patientId = Integer.parseInt(therapyData[4]);
        //only for patient id, it will be replace after
        Patient patient = new Patient();
        patient.setId(patientId);

        Therapy therapy = new Therapy();
        therapy.setId(id);
        therapy.setDescription(description);
        therapy.setStartDate(startDate);
        therapy.setEndDate(endDate);
        therapy.setPatient(patient);

        return therapy;
    }

    public Patient createPatient(String[] patientData, Doctor doctor) {

        int id = Integer.parseInt(patientData[0]);
        String login = patientData[1];
        String password = patientData[2];
        String name = patientData[3];
        LocalDate birthday = LocalDate.parse(patientData[4]);

        Patient patient = new Patient();
        patient.setId(id);
        patient.setLogin(login);
        patient.setPassword(password);
        patient.setName(name);
        patient.setBirthday(birthday);
        patient.setDoctor(doctor);
        addTherapiesToPatient(patient);

        return patient;

    }

    public Patient createPatientFromLine(String line) {

        try {
            List<String> fileData = Files.readAllLines(Paths.get(Constants.FILE_PATH_USERS));
            String[] patData = line.split(Constants.SEPARATOR);
            int patientId = Integer.parseInt(patData[0]);

            int idDoc = getDoctorPatientsIdMap()
                    .entrySet()
                    .stream()
                    .filter(entry -> entry.getValue().contains(patientId))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElseThrow(UserDaoException::new);

            Doctor doctor = fileData
                    .stream()
                    .filter(s -> Integer.parseInt(s.split(Constants.SEPARATOR)[0]) == idDoc)
                    .findFirst()
                    .map(this::createDoctor)
                    .orElseGet(this::createDefaultDoctor);

            return createPatient(patData, doctor);

        } catch (IOException e) {
            throw new UserDaoException("ERROR with user file");
        }
    }

    private Doctor createDefaultDoctor() {
        Doctor doctor = new Doctor();
        doctor.setId(0);
        return doctor;
    }

    public Doctor createDoctor(String lineData) {

        String[] userData = lineData.split(Constants.SEPARATOR);
        int id = Integer.parseInt(userData[0]);
        String login = userData[1];
        String password = userData[2];
        LocalDate birthday = LocalDate.parse(userData[4]);
        String name = userData[3];

        Doctor doctor = new Doctor();
        doctor.setId(id);
        doctor.setLogin(login);
        doctor.setPassword(password);
        doctor.setName(name);
        doctor.setBirthday(birthday);
        addPatientsToDoctor(doctor);

        return doctor;
    }
}
