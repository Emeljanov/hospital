package by.emel.anton.controller;

import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
//@AutoConfigureMockMvc(secure = false)
public class DoctorControllerTest {
    @Test
    public void getDoctorByLoginPass() {
    }

    @Test
    public void setPatientToDoctor() {
    }

    @Test
    public void setTherapyToPatient() {
    }

    @Test
    public void logout() {
    }
}