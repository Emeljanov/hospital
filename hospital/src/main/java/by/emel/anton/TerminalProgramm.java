package by.emel.anton;

import by.emel.anton.model.beans.therapy.OrdinaryTherapy;
import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.beans.users.UserType;
import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.beans.users.doctors.GeneralDoctor;
import by.emel.anton.model.beans.users.patients.OrdinaryPatient;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.model.dao.interfaces.DoctorDAO;
import by.emel.anton.model.dao.interfaces.PatientDAO;
import by.emel.anton.model.dao.interfaces.TherapyDAO;
import by.emel.anton.model.dao.interfaces.UserDAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Optional;

public class TerminalProgramm {
    UserDAO userDAO;
    PatientDAO patientDAO;
    DoctorDAO doctorDAO;
    TherapyDAO therapyDAO;

    public TerminalProgramm(UserDAO userDAO, PatientDAO patientDAO, DoctorDAO doctorDAO, TherapyDAO therapyDAO) {
        this.userDAO = userDAO;
        this.patientDAO = patientDAO;
        this.doctorDAO = doctorDAO;
        this.therapyDAO = therapyDAO;
    }

    public void startProgramm() {

        System.out.println("Hi, you are a doctor or patient? (d/p). If you are new user - create account(new)");

        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {

            String answer = bufferedReader.readLine();

            switch (answer) {
                case "d":
                    System.out.println("You are the doctor! ");
                    enterDoctor(bufferedReader);
                    break;
                case "p":
                    System.out.println("You are the patient!");
                    enterPatient(bufferedReader);
                    break;
                case "new":
                    createNewUser(bufferedReader);
                    break;
            }
        } catch (IOException | UserDAOException e) {
            e.printStackTrace();
        }
    }

    private void createNewUser(BufferedReader bufferedReader) throws IOException {
        System.out.println("Create doctor or patient?");
        String answer = bufferedReader.readLine();

        User user = null;

        switch (answer) {
            case "d":
                user = new GeneralDoctor();
                user.setUserType(UserType.DOCTOR);
                break;
            case "p":
                user = new OrdinaryPatient();
                user.setUserType(UserType.PATIENT);
                break;
        }

        int id = userDAO.getNextId(user);
        System.out.println("Enter login");
        String login = bufferedReader.readLine();
        System.out.println("Enter passwrod");
        String password = bufferedReader.readLine();
        System.out.println("Enter name");
        String name = bufferedReader.readLine();
        System.out.println("Enter birthday yyyy-mm-dd");
        LocalDate birthdday = LocalDate.parse(bufferedReader.readLine());

        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setBirthday(birthdday);
        user.setName(name);

        userDAO.saveUser(user);
        startProgramm();

    }

    private void enterDoctor(BufferedReader bufferedReader) throws IOException, UserDAOException {
        System.out.println("Enter login and password");
        String answer = bufferedReader.readLine();
        String[] userData = answer.split(" ");
        String login = userData[0];
        String password = userData[1];
//        Doctor doctor = doctorDAO.getDoctor(login,password);
        Optional<Doctor> doctor = doctorDAO.getDoctor(login,password);

        doctor.ifPresent(doc -> {

            System.out.println("Hi!" + doc.getName());

            try {
                forDoctor(bufferedReader,doc);
            } catch (IOException | UserDAOException e) {
                e.printStackTrace();
            }

        });

//        System.out.println("Hi! " + doctor.getName());
//        forDoctor(bufferedReader, doctor);

    }

    private void forDoctor(BufferedReader bufferedReader, Doctor doctor) throws IOException, UserDAOException {

        System.out.println("What do you want? See your patients (see)/Add new patient(add)/set therapy(set)?/exit(exit)");

        String answer = bufferedReader.readLine();

        switch (answer) {
            case "add":
                System.out.println("add");
                System.out.println("Enter patient name");
                addPatientToDoctor(bufferedReader, doctor);
                break;
            case "see":
                System.out.println("see");
                System.out.println(doctor.getPatientsId());
                forDoctor(bufferedReader, doctor);
                break;
            case "set":
                System.out.println("set");
                setTherapyToPatinet(bufferedReader, doctor);
                break;
            case "exit":
                startProgramm();
                break;
        }

    }

    private void setTherapyToPatinet(BufferedReader bufferedReader,Doctor doctor) throws IOException, UserDAOException {

        System.out.println("Enter Patient ID");
        int patientId = Integer.parseInt(bufferedReader.readLine());

        Optional<Patient> patient = patientDAO.getPatientById(patientId);
        System.out.println("Enter therapy description");
        String description = bufferedReader.readLine();
        System.out.println("Enter end date (yyyy-mm-dd");
        String endDate = bufferedReader.readLine();

        patient.ifPresent(pat ->{

            int id = therapyDAO.getNextID();
            Therapy therapy = new OrdinaryTherapy(id,description, LocalDate.now(),LocalDate.parse(endDate));
            doctor.setTherapy(pat,therapy);
            userDAO.updateUser(pat);
            therapyDAO.saveTherapy(therapy);

        } );

        forDoctor(bufferedReader,doctor);

    }

    private void addPatientToDoctor(BufferedReader bufferedReader, Doctor doctor) throws IOException, UserDAOException {

        System.out.println("Please, enter patients id");

        int patientId = Integer.parseInt(bufferedReader.readLine());

        Optional<Patient> patient = patientDAO.getPatientById(patientId);

        patient.ifPresent(pat -> {

            doctor.setPatientId(patientId);
            userDAO.updateUser(doctor);
            pat.setDoctorId(doctor.getId());
            userDAO.updateUser(pat);

        });

        forDoctor(bufferedReader,doctor);
    }

    private void enterPatient(BufferedReader bufferedReader) throws IOException, UserDAOException {

        System.out.println("Enter login and password");

        String answer = bufferedReader.readLine();
        String[] userData = answer.split(" ");

        String login = userData[0];
        String password = userData[1];

        Optional<Patient> patient = patientDAO.getPatient(login,password);

        patient.ifPresent(pat -> {

            System.out.println("Hi! " + pat.getName());
            try {
                forPatient(bufferedReader, pat);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });


    }

    private void forPatient(BufferedReader bufferedReader, Patient patient) throws IOException {
        System.out.println("What do you want? See your therapies (therapies)/See you therapy(therapy)?/exit(exit)");

        String answer = bufferedReader.readLine();

        switch (answer) {
            case "therapies":
                System.out.println(patient.getTherapies().toString());
                break;
            case "therapy":
                System.out.println("Please, enter id therapy");
                int id = Integer.parseInt(bufferedReader.readLine());
                Optional<Therapy> therapy = therapyDAO.getTherapy(id);
                therapy.ifPresent(tp -> {
                    System.out.println(tp);
                    try {
                        forPatient(bufferedReader, patient);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                break;
            case "exit":
                startProgramm();
                break;
        }
        forPatient(bufferedReader,patient);

    }
}
