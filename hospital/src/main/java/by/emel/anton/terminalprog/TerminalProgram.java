package by.emel.anton.terminalprog;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.beans.users.doctors.GeneralDoctor;
import by.emel.anton.model.beans.users.patients.OrdinaryPatient;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.TerminalIllegalArgumentException;
import by.emel.anton.model.dao.exceptions.TherapyDAOException;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    private static final String ERROR_ENTER_DOCTOR = "ERROR: doctor -> login or password is incorrect";
    private static final String ERROR_NO_PATIENT_F_BY_ID = "ERROR: no patient found with this id";
    private static final String ERROR_ENTER_PATIENT = "ERROR: patient -> login or password is incorrect";
    private static final String ERROR_ARG_INC = "ERROR: argument is incorrect";

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
                } catch (UserDAOException | TherapyDAOException e) {
                    logger.error(e.getClass().getSimpleName() + Constants.SPACE + e.getMessage());
                }
            }
        }
    }

    public void processingProgram(Scanner scanner) throws  UserDAOException, TherapyDAOException {

        logger.info(START_PROGRAM);
        flag_doctor = true;
        flag_patient = true;
        AnswerType answer;

        try {
            answer = getAnswerAndCheckIllegalArgExp(scanner.nextLine());
        }
        catch (TerminalIllegalArgumentException e) {
            logger.error(e.getClass().getSimpleName() + Constants.SPACE + e.getMessage());
            return;
        }

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

    private void createNewUser(Scanner scanner) throws UserDAOException {

        logger.info(CREATE_NEW_USER);
        AnswerType answer;
        User user = null;

        try {
            answer = getAnswerAndCheckIllegalArgExp(scanner.nextLine());
        }
        catch (TerminalIllegalArgumentException e) {
            logger.error(e.getClass().getSimpleName() + Constants.SPACE + e.getMessage());
            return;
        }

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

    private void enterDoctor(Scanner scanner) throws UserDAOException, TherapyDAOException {

        logger.info(ENTER_LOGIN);
        String login = scanner.nextLine();
        logger.info(ENTER_PASSWORD);
        String password = scanner.nextLine();
        Doctor doctor = userService
                .getDoctor(login,password)
                .orElseThrow(() -> new UserDAOException(ERROR_ENTER_DOCTOR));
        logger.info(HI + doctor.getName());
        startProcessingDoctor(scanner,doctor);

    }

    private void startProcessingDoctor(Scanner scanner, Doctor doctor) throws UserDAOException, TherapyDAOException {

        while (flag_doctor) {
            processingDoctor(scanner,doctor);
        }
        processingProgram(scanner);

    }

    private void processingDoctor(Scanner scanner, Doctor doctor) throws UserDAOException, TherapyDAOException {

        logger.info(PROCESSING_DOCTOR);
        AnswerType answer;

        try {
             answer = getAnswerAndCheckIllegalArgExp(scanner.nextLine());
        }
        catch (TerminalIllegalArgumentException e) {
            logger.error(e.getClass().getSimpleName() + Constants.SPACE + e.getMessage());
            return;
        }

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

    private void setTherapyToPatient(Scanner scanner, Doctor doctor) throws UserDAOException, TherapyDAOException {

        logger.info(ENTER_PATIENT_ID);
        int patientId = Integer.parseInt(scanner.nextLine().trim());
        Patient patient = userService
                .getPatientById(patientId)
                .orElseThrow(() -> new UserDAOException(ERROR_NO_PATIENT_F_BY_ID));
        logger.info(ENTER_THERAPY_DESCR);
        String description = scanner.nextLine();
        logger.info(ENTER_ENDDATE);
        LocalDate endDate = LocalDate.parse(scanner.nextLine());
        userService.addTherapy(doctor,patient,description,endDate);

    }

    private void addPatientToDoctor(Scanner scanner, Doctor doctor) throws UserDAOException  {

        logger.info(ENTER_PATIENT_ID);
        int patientId = scanner.nextInt();
        userService.addPatientToDoctor(doctor,patientId);

    }

    private void enterPatient(Scanner scanner) throws UserDAOException, TherapyDAOException {

        logger.info(ENTER_LOGIN);
        String login = scanner.nextLine();
        logger.info(ENTER_PASSWORD);
        String password = scanner.nextLine();
        Patient patient = userService
                .getPatient(login,password)
                .orElseThrow(() -> new UserDAOException(ERROR_ENTER_PATIENT));
        logger.info(HI + patient.getName());
        startProcessingPatient(scanner, patient);

    }

    private void startProcessingPatient(Scanner scanner, Patient patient) throws  UserDAOException, TherapyDAOException {

        while (flag_patient) {
            processingPatient(scanner,patient);
        }
        processingProgram(scanner);
    }

    private void processingPatient(Scanner scanner, Patient patient) throws TherapyDAOException {

        logger.info(PROCESSING_PATIENT);
        AnswerType answer;

        try {
            answer = getAnswerAndCheckIllegalArgExp(scanner.nextLine());
        }
        catch (TerminalIllegalArgumentException e) {
            logger.error(e.getClass().getSimpleName() + Constants.SPACE + e.getMessage());
            return;
        }

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
    private AnswerType getAnswerAndCheckIllegalArgExp(String line) {
        try{
            return AnswerType.valueOf(line);
        }
        catch (IllegalArgumentException e) {
            throw new TerminalIllegalArgumentException(ERROR_ARG_INC);
        }
    }
}
