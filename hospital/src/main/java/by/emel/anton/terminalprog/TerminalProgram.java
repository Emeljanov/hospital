package by.emel.anton.terminalprog;

import by.emel.anton.constants.Constants;
import by.emel.anton.model.entity.therapy.Therapy;
import by.emel.anton.model.entity.users.User;
import by.emel.anton.model.entity.users.doctors.Doctor;
import by.emel.anton.model.entity.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.TerminalException;
import by.emel.anton.model.dao.exceptions.TherapyDaoException;
import by.emel.anton.model.dao.exceptions.UserDaoException;
import by.emel.anton.service.UserService;
import by.emel.anton.service.UserServiceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
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
            " hibernate(HIBERNATE), spring data(SPRINGDATA) or EXIT";
    private static final String DATA_FROM_FILE = "Data from file";
    private static final String DATA_FROM_JDBC_TEMPLATE = "Data from jdbcTemplate";
    private static final String DATA_FROM_HIBERNATE = "Data from hibernate";
    private static final String DATA_FROM_SPRINGDATA = "Data from spring data";

    private static final Logger logger = LoggerFactory.getLogger(TerminalProgram.class);

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
        logger.info(SELECT_DATA_STORAGE);
        try {
            AnswerType answer = getAnswerAndCheckIllegalArgExp(scanner.nextLine());
            switch (answer) {
                case FILE:
                    logger.info(DATA_FROM_FILE);
                    setUserService(userServiceResolver.resolveUserService(answer));
                    processingProgram(scanner);
                    break;
                case TEMPLATE:
                    logger.info(DATA_FROM_JDBC_TEMPLATE);
                    setUserService(userServiceResolver.resolveUserService(answer));
                    processingProgram(scanner);
                    break;
                case HIBERNATE:
                    logger.info(DATA_FROM_HIBERNATE);
                    setUserService(userServiceResolver.resolveUserService(answer));
                    processingProgram(scanner);
                    break;
                case SPRINGDATA:
                    logger.info(DATA_FROM_SPRINGDATA);
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
            logger.error(e.getClass().getSimpleName() + Constants.SPACE + e.getMessage());
        }
    }

    public void processingProgram(Scanner scanner) {
        while (flag_processing_program) {
            enterUser(scanner);
        }
    }


    public void enterUser(Scanner scanner) {

        logger.info(START_PROGRAM);
        flag_doctor = true;
        flag_patient = true;

        try {
            AnswerType answer = getAnswerAndCheckIllegalArgExp(scanner.nextLine());
            switch (answer) {
                case DOCTOR:
                    logger.info(ANSWER_DOCTOR);
                    enterDoctor(scanner);
                    break;
                case PATIENT:
                    logger.info(ANSWER_PATIENT);
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
        } catch (TerminalException | UserDaoException | TherapyDaoException e) {
            logger.error(e.getClass().getSimpleName() + Constants.SPACE + e.getMessage());
        }
    }

    private void createNewUser(Scanner scanner) {

        logger.info(CREATE_NEW_USER);

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
            logger.info(ENTER_LOGIN);
            String login = scanner.nextLine();
            logger.info(ENTER_PASSWORD);
            String password = scanner.nextLine();
            logger.info(ENTER_NAME);
            String name = scanner.nextLine();
            logger.info(ENTER_BIRTHDAY);
            LocalDate birthday = getDateAndCheckDateTimeExp(scanner.nextLine());

            userService.createUser(user, login, password, name, birthday, true);

        } catch (TerminalException e) {
            logger.error(e.getClass().getSimpleName() + Constants.SPACE + e.getMessage());
        }
    }

    private void enterDoctor(Scanner scanner) {

        logger.info(ENTER_LOGIN);
        String login = scanner.nextLine();
        logger.info(ENTER_PASSWORD);
        String password = scanner.nextLine();
        Doctor doctor = userService
                .getDoctor(login, password)
                .orElseThrow(() -> new UserDaoException(ERROR_ENTER_DOCTOR));
        logger.info(HI + doctor.getName());

        startProcessingDoctor(scanner, doctor);

    }

    private void startProcessingDoctor(Scanner scanner, Doctor doctor) {

        while (flag_doctor) {
            processingDoctor(scanner, doctor);
        }
        processingProgram(scanner);

    }

    private void processingDoctor(Scanner scanner, Doctor doctor) {

        logger.info(PROCESSING_DOCTOR);

        try {

            AnswerType answer = getAnswerAndCheckIllegalArgExp(scanner.nextLine());
            switch (answer) {
                case ADD:
                    logger.info(AnswerType.ADD.toString());
                    addPatientToDoctor(scanner, doctor);
                    break;
                case SEE:
                    logger.info(AnswerType.SEE.toString());
                    Optional<List<Patient>> patients = Optional.ofNullable(doctor.getPatients());
                    patients.ifPresentOrElse(p ->
                                    logger.info(p
                                            .stream()
                                            .map(Patient::getId)
                                            .collect(Collectors.toList())
                                            .toString()),
                            () -> logger.info("[]"));
                    break;
                case SET:
                    logger.info(AnswerType.SET.toString());
                    setTherapyToPatient(scanner, doctor);
                    break;
                case BACK:
                    logger.info(AnswerType.EXIT.toString());
                    flag_doctor = false;
                    break;
                default:
                    throw new TerminalException(ERROR_ARG_INC);
            }

        } catch (TerminalException e) {
            logger.error(e.getClass().getSimpleName() + Constants.SPACE + e.getMessage());
        }

    }

    private void setTherapyToPatient(Scanner scanner, Doctor doctor) throws TerminalException {

        logger.info(ENTER_PATIENT_ID);
        int patientId = Integer.parseInt(scanner.nextLine().trim());
        Optional<List<Patient>> optionalPatients = Optional.ofNullable(doctor.getPatients());

        if (!optionalPatients.isPresent()) {
            throw new UserDaoException("No patients list");
        }
        Optional<Patient> optPatient = optionalPatients.get().stream().filter(pat -> pat.getId() == patientId).findFirst();
        if (!optPatient.isPresent())
            throw new UserDaoException("Patien with such id doesn't exist or connect with this doctor");
        Patient patient = optPatient.get();
        logger.info(ENTER_THERAPY_DESCR);
        String description = scanner.nextLine();
        logger.info(ENTER_ENDDATE);
        LocalDate endDate = getDateAndCheckDateTimeExp(scanner.nextLine());

        userService.addTherapy(patient, description, endDate);
    }

    private void addPatientToDoctor(Scanner scanner, Doctor doctor) throws UserDaoException {

        logger.info(ENTER_PATIENT_ID);
        int patientId = Integer.parseInt(scanner.nextLine().trim());

        userService.addPatientToDoctor(doctor, patientId);

    }

    private void enterPatient(Scanner scanner) {

        logger.info(ENTER_LOGIN);
        String login = scanner.nextLine();
        logger.info(ENTER_PASSWORD);
        String password = scanner.nextLine();
        Patient patient = userService
                .getPatient(login, password)
                .orElseThrow(() -> new UserDaoException(ERROR_ENTER_PATIENT));
        logger.info(HI + patient.getName());

        startProcessingPatient(scanner, patient);
    }

    private void startProcessingPatient(Scanner scanner, Patient patient) {

        while (flag_patient) {
            processingPatient(scanner, patient);
        }
        processingProgram(scanner);
    }

    private void processingPatient(Scanner scanner, Patient patient) {

        logger.info(PROCESSING_PATIENT);
        Optional<List<Therapy>> therapies = Optional.ofNullable(patient.getTherapies());

        try {
            AnswerType answer = getAnswerAndCheckIllegalArgExp(scanner.nextLine());
            switch (answer) {
                case THERAPIES:
                    therapies.ifPresentOrElse(trps ->
                                    logger.info(trps
                                            .stream()
                                            .map(Therapy::getId)
                                            .collect(Collectors.toList())
                                            .toString()),
                            () -> logger.info("No therapies"));
                    break;
                case THERAPY:
                    therapies.ifPresentOrElse(trps ->
                    {
                        logger.info(ENTER_ID_THERAPY);
                        int therapyId = Integer.parseInt(scanner.nextLine().trim());
                        Optional<Therapy> therapy = trps
                                .stream()
                                .filter(t -> t.getId() == therapyId).findFirst();
                        therapy.ifPresentOrElse(t -> logger.info(t.toString()), () -> logger.error("Wrong id"));
                    }, () -> logger.info("No therapies"));
                    break;
                case BACK:
                    flag_patient = false;
                    break;
                default:
                    throw new TerminalException(ERROR_ARG_INC);
            }
        } catch (TerminalException e) {
            logger.error(e.getClass().getSimpleName() + Constants.SPACE + e.getMessage());
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
