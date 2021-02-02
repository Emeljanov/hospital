package by.emel.anton.facade.doctor;

import by.emel.anton.facade.converter.Converter;
import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.service.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class DoctorFacadeImplTest {

    @Mock
    private UserService userService;

    private Converter<Doctor, ResponseDoctorDTO> converter;

    @Mock
    private HttpSession httpSession;


    @Before
    public void setUp() throws Exception {
       /* Doctor doctor = new Doctor();
        doctor.setId(1);
        doctor.setLogin("login");
        doctor.setPassword("password");

        Mockito.when(userService.getDoctor("login","passoword")).thenReturn(Optional.of(doctor));*/

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getDoctorByLoginPassword() {
       /* Doctor doctor = new Doctor();
        doctor.setId(1);
        doctor.setLogin("login");
        doctor.setPassword("password");
        MockitoAnnotations.initMocks(this);

//        Mockito.when(userService.getDoctor("login","passoword")).thenReturn(Optional.of(doctor));




        String l = "login";
        String p = "password";
        Mockito.when(userService.getDoctor(l,p)).thenReturn(Optional.of(doctor)).thenThrow(IllegalArgumentException.class);*/


    }


    @Test
    public void setPatientToDoctor() {
    }

    @Test
    public void setTherapyToPatient() {
    }
}
