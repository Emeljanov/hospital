package by.emel.anton.facade.doctor;

import by.emel.anton.facade.converter.Converter;
import by.emel.anton.facade.therapy.RequestTherapyDTO;
import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.beans.users.User;
import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDaoException;
import by.emel.anton.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DoctorFacadeImplTest {
    @InjectMocks
    private DoctorFacadeImpl doctorFacade;
    @Mock
    private UserService userService;
    @Mock
    private Converter<Doctor, ResponseDoctorDTO> converter;
    @Mock
    private HttpSession httpSession;

    private Doctor doctor;
    private Patient patient10;
    private Patient patient13;
    private RequestTherapyDTO requestTherapyDTO;

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String DOCTOR_ID = "doctorId";
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
    public void getDoctorByLoginPassword() {
        ResponseDoctorDTO responseDoctorDTO = new ResponseDoctorDTO();
        responseDoctorDTO.setLogin(doctor.getLogin());
        responseDoctorDTO.setName(doctor.getName());
        responseDoctorDTO.setId(doctor.getId());
        responseDoctorDTO.setPatientIds(doctor.getPatients().stream().map(User::getId).collect(Collectors.toList()));

        when(userService.getDoctor(LOGIN, PASSWORD)).thenReturn(Optional.of(doctor));
        when(converter.convert(doctor)).thenReturn(responseDoctorDTO);
        ResponseDoctorDTO doctorByLoginPassword = doctorFacade.getDoctorByLoginPassword(LOGIN, PASSWORD);

        assertEquals(doctorByLoginPassword.getId(), doctor.getId());
        assertEquals(doctorByLoginPassword.getName(), doctor.getName());
        assertEquals(doctorByLoginPassword.getLogin(), doctor.getLogin());
        int size = doctor.getPatients().size();
        assertThat(doctorByLoginPassword.getPatientIds()).hasSize(size).containsExactlyInAnyOrder(10, 13);
        verify(httpSession).setAttribute(eq(DOCTOR_ID), eq(doctor.getId()));
    }

    @Test
    public void setPatientToDoctorException() {
        int doctorId = doctor.getId();
        int patientId = patient10.getId();
        when(userService.getDoctorById(doctorId)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserDaoException.class, () -> doctorFacade.setPatientToDoctor(doctorId, patientId));
    }

    @Test
    public void setPatientToDoctor() {
        int doctorId = doctor.getId();
        int patientId = patient10.getId();
        when(userService.getDoctorById(doctorId)).thenReturn(Optional.of(doctor));
        doctorFacade.setPatientToDoctor(doctorId, patientId);
        verify(userService).addPatientToDoctor(doctor, patientId);
    }

    @Test
    public void setTherapyToPatientExceptionPatient() {
        int patientId = patient10.getId();
        int doctorId = doctor.getId();
        when(userService.getDoctorById(doctorId)).thenReturn(Optional.of(doctor));
        when(userService.getPatientById(patientId)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserDaoException.class, () -> doctorFacade.setTherapyToPatient(doctorId, requestTherapyDTO));
    }

    @Test
    public void setTherapyToPatientExceptionDoctor() {
        int doctorId = doctor.getId();
        when(userService.getDoctorById(doctorId)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserDaoException.class, () -> doctorFacade.setTherapyToPatient(doctorId, requestTherapyDTO));
    }

    @Test
    public void setTherapyToPatient() {
        int doctorId = doctor.getId();
        int patientId = patient10.getId();
        Therapy therapy = new Therapy();
        therapy.setPatient(patient10);
        therapy.setDescription(requestTherapyDTO.getDescription());
        therapy.setEndDate(requestTherapyDTO.getEndDate());
        therapy.setStartDate(requestTherapyDTO.getStartDate());

        when(userService.getDoctorById(doctorId)).thenReturn(Optional.of(doctor));
        when(userService.getPatientById(patientId)).thenReturn(Optional.of(patient10));
        doctorFacade.setTherapyToPatient(doctorId, requestTherapyDTO);
        verify(userService).saveTherapy(therapy);
    }
}

