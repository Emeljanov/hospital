package by.emel.anton.facade.doctor;

import by.emel.anton.config.service.SecurityService;
import by.emel.anton.config.service.SecurityServiceImpl;
import by.emel.anton.facade.therapy.RequestTherapyDTO;
import by.emel.anton.model.dao.exceptions.UserDaoException;
import by.emel.anton.model.entity.therapy.Therapy;
import by.emel.anton.model.entity.users.doctors.Doctor;
import by.emel.anton.model.entity.users.patients.Patient;
import by.emel.anton.service.UserService;
import by.emel.anton.service.exception.UserServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DoctorFacadeImplTest {
    @InjectMocks
    private DoctorFacadeImpl doctorFacade;
    @Mock
    private UserService userService;
    @Mock
    private SecurityService securityService;

    private Doctor doctor;
    private Patient patient10;
    private Patient patient13;
    private RequestTherapyDTO requestTherapyDTO;

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";

    @BeforeEach
    public void init() {

        doctor = new Doctor();
        doctor.setPassword(PASSWORD);
        doctor.setLogin(LOGIN);
        doctor.setId(1);
        doctor.setName(NAME);
        doctor.setBirthday(LocalDate.of(2000, 1, 1));
        doctor.setPatients(new ArrayList<>());
        patient10 = new Patient();
        patient10.setId(10);
        patient13 = new Patient();
        patient13.setId(13);
        doctor.addPatient(patient10);
        doctor.addPatient(patient13);

        requestTherapyDTO = new RequestTherapyDTO();
        requestTherapyDTO.setDescription("description");
        requestTherapyDTO.setEndDate(LocalDate.of(2100, 1, 1));
        requestTherapyDTO.setStartDate(LocalDate.of(2000, 1, 1));
        requestTherapyDTO.setPatientId(10);

    }

    @Test
    public void shouldThrowUserDaoExceptionWhenDoctorNotFound() throws UserServiceException {

        String doctorLogin = doctor.getLogin();
        int patientId = patient10.getId();

        when(userService.getDoctorByLogin(doctorLogin)).thenReturn(Optional.empty());
        when(securityService.getLoginFromUserDetails()).thenReturn(doctorLogin);

        Assertions.assertThrows(UserDaoException.class, () -> doctorFacade.setPatientToDoctor(patientId));
    }

    @Test
    public void shouldAssignPatientToDoctor() throws UserServiceException {

        String doctorLogin = doctor.getLogin();
        int patientId = patient10.getId();

        when(userService.getDoctorByLogin(doctorLogin)).thenReturn(Optional.of(doctor));
        when(securityService.getLoginFromUserDetails()).thenReturn(doctorLogin);

        doctorFacade.setPatientToDoctor(patientId);
        verify(userService).addPatientToDoctor(doctor, patientId);
    }

    @Test
    public void shouldThrowUserDaoExceptionWhenPatientNotFound() throws UserServiceException {

        int patientId = patient10.getId();

        when(userService.getPatientById(patientId)).thenReturn(Optional.empty());

        Assertions.assertThrows(UserDaoException.class, () -> doctorFacade.setTherapyToPatient(requestTherapyDTO));
    }

    @Test
    public void shouldAssignTherapyToPatient() throws UserServiceException {

        int patientId = patient10.getId();

        Therapy therapy = new Therapy();
        therapy.setPatient(patient10);
        therapy.setDescription(requestTherapyDTO.getDescription());
        therapy.setEndDate(requestTherapyDTO.getEndDate());
        therapy.setStartDate(requestTherapyDTO.getStartDate());

        when(userService.getPatientById(patientId)).thenReturn(Optional.of(patient10));

        doctorFacade.setTherapyToPatient(requestTherapyDTO);
        verify(userService).saveTherapy(therapy);
    }
}

