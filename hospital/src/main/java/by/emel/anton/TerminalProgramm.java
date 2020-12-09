package by.emel.anton;

import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.beans.users.UserType;
import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.beans.users.doctors.GeneralDoctor;
import by.emel.anton.model.beans.users.patients.OrdinaryPatient;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Optional;

public class TerminalProgramm {

    private final UserService userService;

    public TerminalProgramm(UserService userService) {
        this.userService = userService;
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
            startProgramm();
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
                break;
            case "p":
                user = new OrdinaryPatient();
                break;
        }

        System.out.println("Enter login");
        String login = bufferedReader.readLine();
        System.out.println("Enter passwrod");
        String password = bufferedReader.readLine();
        System.out.println("Enter name");
        String name = bufferedReader.readLine();
        System.out.println("Enter birthday yyyy-mm-dd");
        LocalDate birthdday = LocalDate.parse(bufferedReader.readLine());

        userService.setUserData(user,login,password,name,birthdday);
        userService.saveUser(user);

    }

    private void enterDoctor(BufferedReader bufferedReader) throws IOException, UserDAOException {

        System.out.println("Enter login and password");
        String answer = bufferedReader.readLine();
        String[] userData = answer.split(" ");
        String login = userData[0];
        String password = userData[1];

        Optional<Doctor> doctor = userService.getDoctor(login,password);

        doctor.ifPresent(doc -> {

            System.out.println("Hi!" + doc.getName());

            try {
                processingDoctor(bufferedReader,doc);
            } catch (IOException | UserDAOException e) {
                e.printStackTrace();
            }

        });

    }

    private void processingDoctor(BufferedReader bufferedReader, Doctor doctor) throws IOException, UserDAOException {

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
                break;
            case "set":
                System.out.println("set");
                setTherapyToPatient(bufferedReader, doctor);
                break;
            case "exit":
                startProgramm();
                break;
        }
        processingDoctor(bufferedReader,doctor);

    }

    private void setTherapyToPatient(BufferedReader bufferedReader, Doctor doctor) throws IOException, UserDAOException {

        System.out.println("Enter Patient ID");
        int patientId = Integer.parseInt(bufferedReader.readLine());

        System.out.println("Enter therapy description");
        String description = bufferedReader.readLine();

        System.out.println("Enter end date (yyyy-mm-dd)");
        String endDate = bufferedReader.readLine();

        Optional<Patient> patient = userService.getPatientById(patientId);
        patient.ifPresent(pat -> userService.addTherapy(doctor,pat,description,LocalDate.parse(endDate)));

    }

    private void addPatientToDoctor(BufferedReader bufferedReader, Doctor doctor) throws IOException, UserDAOException {

        System.out.println("Please, enter patients id");

        int patientId = Integer.parseInt(bufferedReader.readLine());

        userService.addPatientToDoctor(doctor,patientId);

    }

    private void enterPatient(BufferedReader bufferedReader) throws IOException, UserDAOException {

        System.out.println("Enter login and password");

        String answer = bufferedReader.readLine();
        String[] userData = answer.split(" ");

        String login = userData[0];
        String password = userData[1];

        Optional<Patient> patient = userService.getPatient(login,password);

        patient.ifPresent(pat -> {

            System.out.println("Hi! " + pat.getName());
            try {
                processingPatient(bufferedReader, pat);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });


    }

    private void processingPatient(BufferedReader bufferedReader, Patient patient) throws IOException {
        System.out.println("What do you want? See your therapies (therapies)/See you therapy(therapy)?/exit(exit)");

        String answer = bufferedReader.readLine();

        switch (answer) {
            case "therapies":
                System.out.println(patient.getTherapies().toString());
                break;
            case "therapy":
                System.out.println("Please, enter id therapy");
                int therapyId = Integer.parseInt(bufferedReader.readLine());
                Optional<Therapy> therapy = userService.getTherapy(therapyId);
                therapy.ifPresent(tp -> {
                    System.out.println(tp);
                    try {
                        processingPatient(bufferedReader, patient);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                break;
            case "exit":
                startProgramm();
                break;
        }
        processingPatient(bufferedReader,patient);

    }
}
