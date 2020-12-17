package by.emel.anton.terminalprog;

import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.beans.users.doctors.GeneralDoctor;
import by.emel.anton.model.beans.users.patients.OrdinaryPatient;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

@Component
public class TerminalProgram {

    private static final String START_PROGRAM = "Hi, you are a doctor or patient? (D/P). If you are new user - create account(N). Exit(EXIT)";
    private static final String ANSWER_DOCTOR = "You are the doctor.";
    private static final String ANSWER_PATIENT = "You are the patient";
    private static final String CREATE_NEW_USER = "Create doctor(D) or patient(P)?";
    private static final String ENTER_LOGIN = "Enter your login";
    private static final String ENTER_PASSWORD = "Enter your password";
    private static final String ENTER_NAME = "Enter your name";
    private static final String ENTER_BIRTHDAY = "Enter you birthday yyyy-mm-dd";
    private static final String HI = "Hi!";
    private static final String PROCESSING_DOCTOR = "What do you want? See your patients (SEE)/Add new patient(ADD)/set therapy(SET)?/exit(EXIT)";
    private static final String ENTER_PATIENT_ID = "Enter patient id";
    private static final String ENTER_THERAPY_DESCR = "Enter therapy description";
    private static final String ENTER_ENDDATE = "Enter end date (yyyy-mm-dd)";
    private static final String PROCESSING_PATIENT = "What do you want? See your therapies (TS)/See you therapy(T)?/exit(EXIT)";
    private static final String ENTER_ID_THERAPY = "Please, enter id therapy";
    private static final String LOGGER_NAME = "LoggerForTerminal";
    private final Logger logger = LoggerFactory.getLogger(LOGGER_NAME);



    private final UserService userService;

    @Autowired
    public TerminalProgram(UserService userService) {
        this.userService = userService;
    }

    private boolean flag_program = true;
    private boolean flag_doctor = true;
    private boolean flag_patient = true;

    public void startProgram() {

        try(Scanner scanner = new Scanner(System.in)) {
            while (flag_program) {
                try {
                    processingProgram(scanner);
                } catch (IOException | UserDAOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void processingProgram(Scanner scanner) throws IOException, UserDAOException {

        logger.info(START_PROGRAM);
        flag_doctor = true;
        flag_patient = true;
        AnswerType answer = AnswerType.valueOf(scanner.nextLine());

        switch (answer) {
            case D:
                logger.info(ANSWER_DOCTOR);
                enterDoctor(scanner);
                break;
            case P:
                logger.info(ANSWER_PATIENT);
                enterPatient(scanner);
                break;
            case N:
                createNewUser(scanner);
                break;
            case EXIT:
                flag_program = false;
                break;
        }

    }

    private void createNewUser(Scanner scanner) throws IOException {

        logger.info(CREATE_NEW_USER);
        AnswerType answer = AnswerType.valueOf(scanner.nextLine());
        User user = null;

        switch (answer) {
            case D:
                user = new GeneralDoctor();
                break;
            case P:
                user = new OrdinaryPatient();
                break;
        }

        logger.info(ENTER_LOGIN);
        String login = scanner.nextLine();
        logger.info(ENTER_PASSWORD);
        String password = scanner.nextLine();
        logger.info(ENTER_NAME);
        String name = scanner.nextLine();
        logger.info(ENTER_BIRTHDAY);
        LocalDate birthday = LocalDate.parse(scanner.nextLine());

        userService.createUser(user,login,password,name,birthday,true);
    }

    private void enterDoctor(Scanner scanner) throws UserDAOException, IOException {

        logger.info(ENTER_LOGIN);
        String login = scanner.nextLine();
        logger.info(ENTER_PASSWORD);
        String password = scanner.nextLine();

        Optional<Doctor> doctor = userService.getDoctor(login,password);
        doctor.ifPresent(doc -> {

            logger.info(HI + doc.getName());

            try {
                startProcessingDoctor(scanner,doc);
            } catch (UserDAOException | IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void startProcessingDoctor(Scanner scanner, Doctor doctor) throws IOException, UserDAOException {
        while (flag_doctor) {
            processingDoctor(scanner,doctor);
        }
        processingProgram(scanner);
    }

    private void processingDoctor(Scanner scanner, Doctor doctor) throws UserDAOException, IOException {

        logger.info(PROCESSING_DOCTOR);
        AnswerType answer = AnswerType.valueOf(scanner.nextLine());

        switch (answer) {
            case ADD:
                logger.info(AnswerType.ADD.toString());
                addPatientToDoctor(scanner, doctor);
                break;
            case SEE:
                logger.info(AnswerType.SEE.toString());
                System.out.println(doctor.getPatientsId());
                break;
            case SET:
                logger.info(AnswerType.SET.toString());
                setTherapyToPatient(scanner, doctor);
                break;
            case EXIT:
                logger.info(AnswerType.EXIT.toString());
                flag_doctor = false;
                break;
        }

    }

    private void setTherapyToPatient(Scanner scanner, Doctor doctor) throws UserDAOException, IOException {

        logger.info(ENTER_PATIENT_ID);
        int patientId = Integer.parseInt(scanner.nextLine().trim());

        logger.info(ENTER_THERAPY_DESCR);
        String description = scanner.nextLine();

        logger.info(ENTER_ENDDATE);
        LocalDate endDate = LocalDate.parse(scanner.nextLine());

        Optional<Patient> patient = userService.getPatientById(patientId);
        patient.ifPresent(pat -> {
            try {
                userService.addTherapy(doctor,pat,description,endDate);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private void addPatientToDoctor(Scanner scanner, Doctor doctor) throws UserDAOException, IOException {

        logger.info(ENTER_PATIENT_ID);
        int patientId = scanner.nextInt();
        userService.addPatientToDoctor(doctor,patientId);

    }

    private void enterPatient(Scanner scanner) throws UserDAOException, IOException {

        logger.info(ENTER_LOGIN);
        String login = scanner.nextLine();
        logger.info(ENTER_PASSWORD);
        String password = scanner.nextLine();
        Optional<Patient> patient = userService.getPatient(login,password);

        patient.ifPresent(pat -> {

            logger.info(HI + pat.getName());
            try {
                startProcessingPatient(scanner, pat);
            } catch (IOException | UserDAOException e) {
                e.printStackTrace();
            }
        });

    }

    private void startProcessingPatient(Scanner scanner, Patient patient) throws IOException, UserDAOException {

        while (flag_patient) {
            processingPatient(scanner,patient);
        }
        processingProgram(scanner);
    }

    private void processingPatient(Scanner scanner, Patient patient) throws IOException {

        logger.info(PROCESSING_PATIENT);
        String line = scanner.nextLine();
        AnswerType answer = AnswerType.valueOf(line);

        switch (answer) {
            case TS:
                logger.info(patient.getTherapies().toString());
                break;
            case T:
                logger.info(ENTER_ID_THERAPY);
                int therapyId = Integer.parseInt(scanner.nextLine().trim());
                Optional<Therapy> therapy = userService.getTherapy(therapyId);
                therapy.ifPresent(tp -> logger.info(tp.toString()));
                break;
            case EXIT:
                flag_patient = false;
                break;
        }
    }
}
