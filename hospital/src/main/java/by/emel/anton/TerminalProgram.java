package by.emel.anton;

import by.emel.anton.constants.Constans;
import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.beans.users.User;
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
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TerminalProgram {

    private static final String START_PROGRAM = "Hi, you are a doctor or patient? (d/p). If you are new user - create account(new)";
    private static final String ANSWER_D = "d";
    private static final String ANSWER_P = "p";
    private static final String ANSWER_N = "n";
    private static final String ANSWER_DOCTOR = "You are the doctor.";
    private static final String ANSWER_PATIENT = "You are the patient";
    private static final String CREATE_NEW_USER = "Create doctor(d) or patient(p)?";
    private static final String ENTER_LOGIN = "Enter your login";
    private static final String ENTER_PASSWORD = "Enter your password";
    private static final String ENTER_LOG_PASSW = "Enter your login and password";
    private static final String ENTER_NAME = "Enter your name";
    private static final String ENTER_BIRTHDAY = "Enter you birthday yyyy-mm-dd";
    private static final String HI = "Hi!";
    private static final String PROCESSING_DOCTOR = "What do you want? See your patients (see)/Add new patient(add)/set therapy(set)?/exit(exit)";
    private static final String ADD = "add";
    private static final String SEE = "see";
    private static final String SET = "set";
    private static final String EXIT = "exit";
    private static final String ENTER_PATIENT_ID = "Enter patient id";
    private static final String ENTER_THERAPY_DESCR = "Enter therapy description";
    private static final String ENTER_ENDDATE = "Enter end date (yyyy-mm-dd)";
    private static final String PROCESSING_PATIENT = "What do you want? See your therapies (ts)/See you therapy(t)?/exit(exit)";
    private static final String TS = "ts";
    private static final String T = "t";
    private static final String ENTER_ID_THERAPY = "Please, enter id therapy";
    private static final String LOGGER_NAME = "LoggerForTerminal";


    private final Logger logger = LoggerFactory.getLogger(LOGGER_NAME);
    private final UserService userService;

    public TerminalProgram(UserService userService) {
        this.userService = userService;
    }

    public void startProgram() {

        logger.info(START_PROGRAM);

        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {

            String answer = bufferedReader.readLine();

            switch (answer) {
                case ANSWER_D:
                    logger.info(ANSWER_DOCTOR);
                    enterDoctor(bufferedReader);
                    break;
                case ANSWER_P:
                    logger.info(ANSWER_PATIENT);
                    enterPatient(bufferedReader);
                    break;
                case ANSWER_N:
                    createNewUser(bufferedReader);
                    break;
            }
            startProgram();
        } catch (IOException | UserDAOException e) {
            e.printStackTrace();
        }

    }

    private void createNewUser(BufferedReader bufferedReader) throws IOException {

        logger.info(CREATE_NEW_USER);
        String answer = bufferedReader.readLine();

        User user = null;

        switch (answer) {
            case ANSWER_D:
                user = new GeneralDoctor();
                break;
            case ANSWER_P:
                user = new OrdinaryPatient();
                break;
        }

        logger.info(ENTER_LOGIN);
        String login = bufferedReader.readLine();
        logger.info(ENTER_PASSWORD);
        String password = bufferedReader.readLine();
        logger.info(ENTER_NAME);
        String name = bufferedReader.readLine();
        logger.info(ENTER_BIRTHDAY);
        LocalDate birthdday = LocalDate.parse(bufferedReader.readLine());

        userService.setUserData(user,login,password,name,birthdday);
        userService.saveUser(user);

    }

    private void enterDoctor(BufferedReader bufferedReader) throws IOException, UserDAOException {

        logger.info(ENTER_LOG_PASSW);
        String answer = bufferedReader.readLine();
        String[] userData = answer.split(Constans.SPACE);
        String login = userData[0];
        String password = userData[1];

        Optional<Doctor> doctor = userService.getDoctor(login,password);

        doctor.ifPresent(doc -> {

            logger.info(HI + doc.getName());

            try {
                processingDoctor(bufferedReader,doc);
            } catch (IOException | UserDAOException e) {
                e.printStackTrace();
            }

        });

    }

    private void processingDoctor(BufferedReader bufferedReader, Doctor doctor) throws IOException, UserDAOException {

        logger.info(PROCESSING_DOCTOR);

        String answer = bufferedReader.readLine();

        switch (answer) {
            case ADD:
                logger.info(ADD);
                addPatientToDoctor(bufferedReader, doctor);
                break;
            case SEE:
                logger.info(SEE);
                System.out.println(doctor.getPatientsId());
                break;
            case SET:
                logger.info(SET);
                setTherapyToPatient(bufferedReader, doctor);
                break;
            case EXIT:
                logger.info(EXIT);
                startProgram();
                break;
        }
        processingDoctor(bufferedReader,doctor);

    }

    private void setTherapyToPatient(BufferedReader bufferedReader, Doctor doctor) throws IOException, UserDAOException {

        logger.info(ENTER_PATIENT_ID);
        int patientId = Integer.parseInt(bufferedReader.readLine());

        logger.info(ENTER_THERAPY_DESCR);
        String description = bufferedReader.readLine();

        logger.info(ENTER_ENDDATE);
        String endDate = bufferedReader.readLine();

        Optional<Patient> patient = userService.getPatientById(patientId);
        patient.ifPresent(pat -> userService.addTherapy(doctor,pat,description,LocalDate.parse(endDate)));

    }

    private void addPatientToDoctor(BufferedReader bufferedReader, Doctor doctor) throws IOException, UserDAOException {

        logger.info(ENTER_PATIENT_ID);

        int patientId = Integer.parseInt(bufferedReader.readLine());

        userService.addPatientToDoctor(doctor,patientId);

    }

    private void enterPatient(BufferedReader bufferedReader) throws IOException, UserDAOException {

        logger.info(ENTER_LOG_PASSW);

        String answer = bufferedReader.readLine();
        String[] userData = answer.split(Constans.SPACE);

        String login = userData[0];
        String password = userData[1];

        Optional<Patient> patient = userService.getPatient(login,password);

        patient.ifPresent(pat -> {

            logger.info(HI + pat.getName());
            try {
                processingPatient(bufferedReader, pat);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });


    }

    private void processingPatient(BufferedReader bufferedReader, Patient patient) throws IOException {

        logger.info(PROCESSING_PATIENT);

        String answer = bufferedReader.readLine();

        switch (answer) {
            case TS:
                logger.info(patient.getTherapies().toString());
                break;
            case T:
                logger.info(ENTER_ID_THERAPY);
                int therapyId = Integer.parseInt(bufferedReader.readLine());
                Optional<Therapy> therapy = userService.getTherapy(therapyId);
                therapy.ifPresent(tp -> {
                    logger.info(tp.toString());
                    try {
                        processingPatient(bufferedReader, patient);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                break;
            case EXIT:
                startProgram();
                break;
        }
        processingPatient(bufferedReader,patient);

    }
}
