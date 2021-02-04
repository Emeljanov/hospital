package by.emel.anton.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
//@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
public class DoctorControllerTest {

    private DoctorController doctorController;
    private MockMvc mockMvc;

    @Autowired
    public DoctorControllerTest(MockMvc mockMvc, DoctorController doctorController) {
        this.mockMvc = mockMvc;
        this.doctorController = doctorController;
    }

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