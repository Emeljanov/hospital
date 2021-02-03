package by.emel.anton.facade.doctor;

import by.emel.anton.facade.converter.DoctorConverter;
import by.emel.anton.facade.therapy.RequestTherapyDTO;
import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.exceptions.UserDaoException;
import by.emel.anton.service.UserService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class DoctorFacadeImplTest {
    @InjectMocks
    private DoctorFacadeImpl doctorFacade;
    @Mock
    private UserService userService;
    @Spy
    private DoctorConverter converter;
    @Mock
    private HttpSession httpSession;

    private static Doctor doctor;
    private static RequestTherapyDTO requestTherapyDTO;

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String DOCTOR_ID = "doctorId";

    @BeforeClass
    public static void preparedDoctor() {

        doctor = new Doctor();
        doctor.setPassword("password");
        doctor.setLogin("login");
        doctor.setId(1);
        doctor.setName("name");
        doctor.setBirthday(LocalDate.of(2000, 1, 1));
        doctor.setPatients(new ArrayList<>());
        Patient patient10 = new Patient();
        patient10.setId(10);
        Patient patient13 = new Patient();
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
        when(userService.getDoctor(LOGIN, PASSWORD)).thenReturn(Optional.of(doctor));
        ResponseDoctorDTO doctorByLoginPassword = doctorFacade.getDoctorByLoginPassword(LOGIN, PASSWORD);

        assertEquals(doctorByLoginPassword.getId(), doctor.getId());
        assertEquals(doctorByLoginPassword.getName(), doctor.getName());
        assertEquals(doctorByLoginPassword.getLogin(), doctor.getLogin());
        int size = doctor.getPatients().size();
        assertThat(doctorByLoginPassword.getPatientIds()).hasSize(size).containsExactlyInAnyOrder(10, 13);
        verify(httpSession).setAttribute(eq(DOCTOR_ID), anyInt());
    }


    @Test(expected = UserDaoException.class)
    public void setPatientToDoctorException() {
        when(userService.getDoctorById(10)).thenReturn(Optional.empty());
        doctorFacade.setPatientToDoctor(10, anyInt());
    }

    @Test
    public void setPatientToDoctor() {
        when(userService.getDoctorById(10)).thenReturn(Optional.of(mock(Doctor.class)));
        doctorFacade.setPatientToDoctor(10, anyInt());
        verify(userService).addPatientToDoctor(any(), anyInt());
    }

    @Test(expected = UserDaoException.class)
    public void setTherapyToPatientExceptionPatient() {
        int patientId = requestTherapyDTO.getPatientId();
        when(userService.getDoctorById(10)).thenReturn(Optional.of(mock(Doctor.class)));
        when(userService.getPatientById(patientId)).thenReturn(Optional.empty());
        doctorFacade.setTherapyToPatient(patientId, requestTherapyDTO);
    }

    @Test(expected = UserDaoException.class)
    public void setTherapyToPatientExceptionDoctor() {
        int patientId = requestTherapyDTO.getPatientId();
        when(userService.getDoctorById(10)).thenReturn(Optional.empty());
        doctorFacade.setTherapyToPatient(patientId, requestTherapyDTO);
    }

    @Test
    public void setTherapyToPatient() {
        int patientId = requestTherapyDTO.getPatientId();
        when(userService.getDoctorById(10)).thenReturn(Optional.of(mock(Doctor.class)));
        when(userService.getPatientById(patientId)).thenReturn(Optional.of(mock(Patient.class)));
        doctorFacade.setTherapyToPatient(patientId, requestTherapyDTO);
        verify(userService).saveTherapy(any());
    }

}

