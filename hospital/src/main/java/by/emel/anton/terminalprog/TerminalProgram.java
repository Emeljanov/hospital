package by.emel.anton.terminalprog;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.TerminalException;
import by.emel.anton.model.dao.exceptions.TherapyDAOException;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import by.emel.anton.service.UserService;
import by.emel.anton.service.UserServiceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class TerminalProgram {

    private static final String START_PROGRAM = "Hi, you are a doctor or patient? (DOCTOR/PATIENT). If you are new user - create account(NEW). Back(BACK)";
    private static final String ANSWER_DOCTOR = "You are the doctor.";
    private static final String ANSWER_PATIENT = "You are the patient";
    private static final String CREATE_NEW_USER = "Create doctor(DOCTOR) or patient(PATIENT)?";
    private static final String ENTER_LOGIN = "Enter your login";
    private static final String ENTER_PASSWORD = "Enter your password";
    private static final String ENTER_NAME = "Enter your name";
    private static final String ENTER_BIRTHDAY = "Enter you birthday yyyy-mm-dd";
    private static final String HI = "Hi!";
    private static final String PROCESSING_DOCTOR = "What do you want? See your patients (SEE)/Add new patient(ADD)/set therapy(SET)?/back(BACK)";
    private static final String ENTER_PATIENT_ID = "Enter patient id";
    private static final String ENTER_THERAPY_DESCR = "Enter therapy description";
    private static final String ENTER_ENDDATE = "Enter end date (yyyy-mm-dd)";
    private static final String PROCESSING_PATIENT = "What do you want? See your therapies (THERAPIES)/See you therapy(THERAPY)?/Back(BACK)";
    private static final String ENTER_ID_THERAPY = "Please, enter id therapy";
    private static final String ERROR_ENTER_DOCTOR = "ERROR: doctor -> login or password is incorrect";
    private static final String ERROR_NO_PATIENT_F_BY_ID = "ERROR: no patient found with this id";
    private static final String ERROR_ENTER_PATIENT = "ERROR: patient -> login or password is incorrect";
    private static final String ERROR_ARG_INC = "ERROR: argument is incorrect";
    private static final String SELECT_DATA_STORAGE = "Select data storage: file(FILE), jdbcTemplate(TEMPLATE)," +
            " hibernate(HIBERNATE) or EXIT";
    private static final String DATA_FROM_FILE = "Data from file";
    private static final String DATA_FROM_JDBC_TEMPLATE = "Data from jdbcTemplate";
    private static final String DATA_FROM_HIBERNATE = "Data from hibernate";

    private static final Logger LOGGER = LoggerFactory.getLogger(TerminalProgram.class);

    private UserService userService;
    private final UserServiceResolver userServiceResolver;

    @Autowired
    public TerminalProgram(UserServiceResolver userServiceResolver) {
        this.userServiceResolver = userServiceResolver;
    }

    private boolean flag_program = true;
    private boolean flag_doctor = true;
    private boolean flag_patient = true;
    private boolean flag_processing_program = true;

    public void startProgram() {

        try (Scanner scanner = new Scanner(System.in)) {
            while (flag_program) {
                flag_processing_program = true;
                selectDataStorage(scanner);
            }
        }
    }

    private void selectDataStorage(Scanner scanner) {
        LOGGER.info(SELECT_DATA_STORAGE);
        try {
            AnswerType answer = getAnswerAndCheckIllegalArgExp(scanner.nextLine());
            switch (answer) {
                case FILE:
                    LOGGER.info(DATA_FROM_FILE);
                    setUserService(userServiceResolver.resolveUserService(answer));
                    processingProgram(scanner);
                    break;
                case TEMPLATE:
                    LOGGER.info(DATA_FROM_JDBC_TEMPLATE);
                    setUserService(userServiceResolver.resolveUserService(answer));
                    processingProgram(scanner);
                    break;
                case HIBERNATE:
                    LOGGER.info(DATA_FROM_HIBERNATE);
                    setUserService(userServiceResolver.resolveUserService(answer));
                    processingProgram(scanner);
                    break;
                case EXIT:
                    flag_program = false;
                    break;
                default:
                    throw new TerminalException(ERROR_ARG_INC);
            }

        } catch (TerminalException e) {
            LOGGER.error(e.getClass().getSimpleName() + Constants.SPACE + e.getMessage());
        }
    }

    public void processingProgram(Scanner scanner) {
        while (flag_processing_program) {
            enterUser(scanner);
        }
    }


    public void enterUser(Scanner scanner) {

        LOGGER.info(START_PROGRAM);
        flag_doctor = true;
        flag_patient = true;

        try {
            AnswerType answer = getAnswerAndCheckIllegalArgExp(scanner.nextLine());
            switch (answer) {
                case DOCTOR:
                    LOGGER.info(ANSWER_DOCTOR);
                    enterDoctor(scanner);
                    break;
                case PATIENT:
                    LOGGER.info(ANSWER_PATIENT);
                    enterPatient(scanner);
                    break;
                case NEW:
                    createNewUser(scanner);
                    break;
                case BACK:
                    flag_processing_program = false;
                    break;
                default:
                    throw new TerminalException(ERROR_ARG_INC);
            }
        } catch (TerminalException | UserDAOException | TherapyDAOException e) {
            LOGGER.error(e.getClass().getSimpleName() + Constants.SPACE + e.getMessage());
        }
    }

    private void createNewUser(Scanner scanner) throws UserDAOException {

        LOGGER.info(CREATE_NEW_USER);

        try {
            User user;
            AnswerType answer = getAnswerAndCheckIllegalArgExp(scanner.nextLine());
            switch (answer) {
                case DOCTOR:
                    user = new Doctor();
                    break;
                case PATIENT:
                    user = new Patient();
                    break;
                default:
                    throw new TerminalException(ERROR_ARG_INC);
            }
            LOGGER.info(ENTER_LOGIN);
            String login = scanner.nextLine();
            LOGGER.info(ENTER_PASSWORD);
            String password = scanner.nextLine();
            LOGGER.info(ENTER_NAME);
            String name = scanner.nextLine();
            LOGGER.info(ENTER_BIRTHDAY);
            LocalDate birthday = getDateAndCheckDateTimeExp(scanner.nextLine());

            userService.createUser(user, login, password, name, birthday, true);
        } catch (TerminalException e) {
            LOGGER.error(e.getClass().getSimpleName() + Constants.SPACE + e.getMessage());
        }
    }

    private void enterDoctor(Scanner scanner) throws UserDAOException, TherapyDAOException {

        LOGGER.info(ENTER_LOGIN);
        String login = scanner.nextLine();
        LOGGER.info(ENTER_PASSWORD);
        String password = scanner.nextLine();
        Doctor doctor = userService
                .getDoctor(login, password)
                .orElseThrow(() -> new UserDAOException(ERROR_ENTER_DOCTOR));
        LOGGER.info(HI + doctor.getName());
        startProcessingDoctor(scanner, doctor);

    }

    private void startProcessingDoctor(Scanner scanner, Doctor doctor) throws UserDAOException, TherapyDAOException {

        while (flag_doctor) {
            processingDoctor(scanner, doctor);
        }
        processingProgram(scanner);

    }

    private void processingDoctor(Scanner scanner, Doctor doctor) throws UserDAOException, TherapyDAOException {

        LOGGER.info(PROCESSING_DOCTOR);

        try {

            AnswerType answer = getAnswerAndCheckIllegalArgExp(scanner.nextLine());
            switch (answer) {
                case ADD:
                    LOGGER.info(AnswerType.ADD.toString());
                    addPatientToDoctor(scanner, doctor);
                    break;
                case SEE:
                    LOGGER.info(AnswerType.SEE.toString());
                    System.out.println(doctor.getPatients());
                    break;
                case SET:
                    LOGGER.info(AnswerType.SET.toString());
                    setTherapyToPatient(scanner, doctor);
                    break;
                case BACK:
                    LOGGER.info(AnswerType.EXIT.toString());
                    flag_doctor = false;
                    break;
                default:
                    throw new TerminalException(ERROR_ARG_INC);
            }

        } catch (TerminalException e) {
            LOGGER.error(e.getClass().getSimpleName() + Constants.SPACE + e.getMessage());
        }

    }

    private void setTherapyToPatient(Scanner scanner, Doctor doctor) throws UserDAOException, TherapyDAOException, TerminalException {

        LOGGER.info(ENTER_PATIENT_ID);
        int patientId = Integer.parseInt(scanner.nextLine().trim());
        Patient patient = userService
                .getPatientById(patientId)
                .orElseThrow(() -> new UserDAOException(ERROR_NO_PATIENT_F_BY_ID));
        LOGGER.info(ENTER_THERAPY_DESCR);
        String description = scanner.nextLine();
        LOGGER.info(ENTER_ENDDATE);
        LocalDate endDate = getDateAndCheckDateTimeExp(scanner.nextLine());
        userService.addTherapy(doctor, patient, description, endDate);
    }

    private void addPatientToDoctor(Scanner scanner, Doctor doctor) throws UserDAOException {

        LOGGER.info(ENTER_PATIENT_ID);
        int patientId = Integer.parseInt(scanner.nextLine().trim());
        userService.addPatientToDoctor(doctor, patientId);

    }

    private void enterPatient(Scanner scanner) throws UserDAOException, TherapyDAOException {

        LOGGER.info(ENTER_LOGIN);
        String login = scanner.nextLine();
        LOGGER.info(ENTER_PASSWORD);
        String password = scanner.nextLine();
        Patient patient = userService
                .getPatient(login, password)
                .orElseThrow(() -> new UserDAOException(ERROR_ENTER_PATIENT));
        LOGGER.info(HI + patient.getName());
        startProcessingPatient(scanner, patient);

    }

    private void startProcessingPatient(Scanner scanner, Patient patient) throws TherapyDAOException {

        while (flag_patient) {
            processingPatient(scanner, patient);
        }
        processingProgram(scanner);
    }

    private void processingPatient(Scanner scanner, Patient patient) throws TherapyDAOException {

        LOGGER.info(PROCESSING_PATIENT);

        try {
            AnswerType answer = getAnswerAndCheckIllegalArgExp(scanner.nextLine());
            switch (answer) {
                case THERAPIES:
                    LOGGER.info(patient.getTherapies()
                            .stream()
                            .map(Therapy::getId)
                            .collect(Collectors.toList())
                            .toString());
                    break;
                case THERAPY:
                    LOGGER.info(ENTER_ID_THERAPY);
                    int therapyId = Integer.parseInt(scanner.nextLine().trim());
                    Optional<Therapy> therapy = userService.getTherapy(therapyId);
                    therapy.ifPresent(tp -> LOGGER.info(tp.toString()));
                    break;
                case BACK:
                    flag_patient = false;
                    break;
                default:
                    throw new TerminalException(ERROR_ARG_INC);
            }
        } catch (TerminalException e) {
            LOGGER.error(e.getClass().getSimpleName() + Constants.SPACE + e.getMessage());
        }

    }

    private AnswerType getAnswerAndCheckIllegalArgExp(String line) throws TerminalException {
        try {
            return AnswerType.valueOf(line);
        } catch (IllegalArgumentException e) {
            throw new TerminalException(ERROR_ARG_INC);
        }
    }

    private LocalDate getDateAndCheckDateTimeExp(String line) throws TerminalException {
        try {
            return LocalDate.parse(line);
        } catch (DateTimeException e) {
            throw new TerminalException(ERROR_ARG_INC);
        }
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
