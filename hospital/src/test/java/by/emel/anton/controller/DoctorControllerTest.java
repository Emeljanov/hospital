package by.emel.anton.controller;

import by.emel.anton.facade.doctor.RequestDoctorDTO;
import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.dao.interfaces.UserDAO;
import by.emel.anton.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;


@ExtendWith(SpringExtension.class)
@SpringBootTest
//@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
//@TestPropertySource("/application.properties")

@Sql(value = {"classpath:create-user-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)

public class DoctorControllerTest {

    private DoctorController doctorController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public DoctorControllerTest (MockMvc mockMvc, DoctorController doctorController,ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.doctorController = doctorController;
        this.objectMapper = objectMapper;
    }

    @Test
//    @Transactional
    public void testController() throws Exception {
      /*  Doctor doctor = new Doctor();
        doctor.setPatients(new ArrayList<>());
        doctor.setBirthday(LocalDate.of(2000,01,01));
        doctor.setName("name");
        doctor.setLogin("login");
        doctor.setPassword("password");
        userService.saveUser(doctor);*/

        RequestDoctorDTO requestDoctorDTO = new RequestDoctorDTO();
        requestDoctorDTO.setLogin("log1");
        requestDoctorDTO.setPassword("pas");

//        Assertions.assertNotNull(doctorController);



        System.out.println("fsdfs");
//        this.mockMvc.perform(MockMvcRequestBuilders.post("/login")).andReturn();
//        mockMvc.perform(MockMvcRequestBuilders.post("/doctor/login")).andDo(MockMvcResultHandlers.print());
        mockMvc.perform(MockMvcRequestBuilders.post("/doctor/login")
                .content(objectMapper.writeValueAsString(requestDoctorDTO)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());

//        mockMvc.perform(MockMvcRequestBuilders.post("/doctor/login").content(st).contentType(MediaType.APPLICATION_JSON)).andReturn();
//        System.out.println(mockMvc.perform(MockMvcRequestBuilders.post("/doctor/login").param("login1","1password")).andReturn());
      /*  doctorController.getDoctorByLoginPass(requestDoctorDTO);*/
        Doctor d = new Doctor();
        UserService userService;
       d =  userService.saveUser(d);
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