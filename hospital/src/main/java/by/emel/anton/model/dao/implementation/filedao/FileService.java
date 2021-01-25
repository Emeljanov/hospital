package by.emel.anton.model.dao.implementation.filedao;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class FileService {
/*
    FileDoctorDAO fileDoctorDAO;
    FilePatientDAO filePatientDAO;

    @Autowired
    public FileService(FileDoctorDAO fileDoctorDAO, FilePatientDAO filePatientDAO) {
        this.fileDoctorDAO = fileDoctorDAO;
        this.filePatientDAO = filePatientDAO;
    }*/

    public static boolean isLoginPasswordCorrect(String data, String login, String password) {
        String[] userData = data.split(Constants.SEPARATOR);
        String loginData = userData[1];
        String passwordData = userData[2];

        return loginData.equals(login) && passwordData.equals(password);
    }

    public static boolean findLineById(String data, int id) {
        String[] userData = data.split(Constants.SEPARATOR);
        int idData = Integer.parseInt(userData[0]);

        return id == idData;
    }

    public static int getNextLineId(Path filePath) throws IOException {

        List<String> fileData = Files.readAllLines(filePath);
        String lineMaxID = fileData.stream().max(new LineIdComaparator()).orElse("0/");

        return Integer.parseInt(lineMaxID.split(Constants.SEPARATOR)[0]) + 1;

    }

    public static Map<Integer, List<Integer>> getDoctorPatientsIdMap() throws UserDAOException {
        try {
            return Files.readAllLines(Paths.get(Constants.FILE_PATH_PATIENTS))
                    .stream()
                    .collect(Collectors.groupingBy(line -> Integer.valueOf(line.split(Constants.SEPARATOR)[1]),
                            Collectors.mapping(line -> Integer.valueOf(line.split(Constants.SEPARATOR)[0]), Collectors.toList())));

        } catch (IOException e) {
            throw new UserDAOException("ERROR get Patient id list");
        }
    }

    public static void addTherapiesToPatient(Patient patient) {
        try {
            List<Therapy> trps = new ArrayList<>();

            List<String> listLineThe = Files.readAllLines(Paths.get(Constants.FILE_PATH_THERAPIES)).stream()

                    .filter(l -> Integer.parseInt(l.split(Constants.SEPARATOR)[4]) == patient.getId())
                    .collect(Collectors.toList());

            List<Therapy> therapies = listLineThe.stream()
                    .map(FileService::createTherapyFromLine)
                    .collect(Collectors.toList());
            therapies.forEach(t -> t.setPatient(patient));


            patient.setTherapies(therapies);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addPatientsToDoctor(Doctor doctor) throws UserDAOException {

        List<Patient> patients = new ArrayList<>();

        Optional<List<Integer>> patiendIdList = Optional.ofNullable(getDoctorPatientsIdMap().get(doctor.getId()));

        patiendIdList.ifPresent( pats -> pats.forEach(id -> {
            try {
                Optional<Patient> patient = new FilePatientDAO().getPatientById(id);
                patient.ifPresent(patients::add);
            } catch (UserDAOException e) {
                e.printStackTrace();
            }
        }));

        /*getDoctorPatientsIdMap().get(doctor.getId()).forEach(id -> {
            try {
                Optional<Patient> patient = new FilePatientDAO().getPatientById(id);
                patient.ifPresent(patients::add);
            } catch (UserDAOException e) {
                e.printStackTrace();
            }
        });*/


        doctor.setPatients(patients);
    }

    public static void addDoctorToPatient(Patient patient) throws UserDAOException {
        int patientId = patient.getId();
        Optional<Integer> optionalDocId = getDoctorPatientsIdMap()
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().contains(patientId))
                .map(Map.Entry::getKey)
                .findFirst();

        if (!optionalDocId.isPresent()) {
            throw new UserDAOException("No doctor id in file");
        } else {
            int doctorId = optionalDocId.get();
            Optional<Doctor> optionalDoctor = new FileDoctorDAO().getDoctorById(doctorId);
            optionalDoctor.ifPresentOrElse(patient::setDoctor, () -> {
                Doctor doc = new Doctor();
                doc.setId(0);
                patient.setDoctor(doc);
            });
        }
    }


    public static Therapy createTherapyFromLine(String line) {
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
 /*   //----------------From Patient.class Methods
    public Optional<Patient> getPatient(String login, String password) throws UserDAOException {

        try {
            List<String> fileData = Files.readAllLines(Paths.get(Constants.FILE_PATH_USERS));

            return fileData
                    .stream()
                    .filter(s -> isLoginPasswordCorrect(s, login, password))
                    .findFirst()
                    .map(this::createPatient);
        } catch (IOException e) {
            throw new UserDAOException("ERROR getPatient from file");
        }
    }

    public Optional<Patient> getPatientById(int id) throws UserDAOException {

        try {
            List<String> fileData = Files.readAllLines(Paths.get(Constants.FILE_PATH_USERS));

            return fileData
                    .stream()
                    .filter(s -> findLineById(s, id))
                    .findFirst()
                    .map(this::createPatient);
        } catch (IOException e) {
            throw new UserDAOException("ERROR get Patient by ID from FILE");
        }
    }
    private Patient createPatient(String lineData) {

        String[] userData = lineData.split(Constants.SEPARATOR);
        int id = Integer.parseInt(userData[0]);
        String login = userData[1];
        String password = userData[2];
        String name = userData[3];
        LocalDate birthday = LocalDate.parse(userData[4]);
        Patient patient = new Patient();
        patient.setId(id);
        patient.setLogin(login);
        patient.setPassword(password);
        patient.setName(name);
        patient.setBirthday(birthday);

        try {
            addDoctorToPatient(patient);
        } catch (UserDAOException e) {
            e.printStackTrace();
        }
        addTherapiesToPatient(patient);

        return patient;

    }
    //-------------------Doctor.class Mathod
    public Optional<Doctor> getDoctor(String login, String password) throws UserDAOException {

        try {
            List<String> fileData = Files.readAllLines(Paths.get(Constants.FILE_PATH_USERS));

            return fileData
                    .stream()
                    .filter(s -> isLoginPasswordCorrect(s, login, password))
                    .findFirst()
                    .map(this::createDoctor);
        } catch (IOException e) {
            throw new UserDAOException("ERROR getDoctor from file");
        }

    }

    public Optional<Doctor> getDoctorById(int id) throws UserDAOException {
        try {
            List<String> fileData = Files.readAllLines(Paths.get(Constants.FILE_PATH_USERS));

            return fileData
                    .stream()
                    .filter(s -> Integer.parseInt(s.split(Constants.SEPARATOR)[0])==id)
                    .findFirst()
                    .map(this::createDoctor);
        } catch (IOException e) {
            throw new UserDAOException("ERROR getDoctor from file");
        }
    }
    private Doctor createDoctor(String lineData) {

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

        try {
            addPatientsToDoctor(doctor);
        } catch (UserDAOException e) {
            e.printStackTrace();
        }

        return doctor;

    }*/
}
