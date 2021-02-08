package by.emel.anton.controller;

import by.emel.anton.facade.doctor.RequestDoctorDTO;
import by.emel.anton.facade.doctor.ResponseDoctorDTO;
import by.emel.anton.model.beans.users.doctors.Doctor;
import by.emel.anton.model.beans.users.patients.Patient;
import by.emel.anton.model.dao.implementation.springdatadao.intefaces.DoctorJpaRepository;
import by.emel.anton.model.dao.implementation.springdatadao.intefaces.PatientJpaRepository;
import by.emel.anton.model.dao.implementation.springdatadao.intefaces.TherapyJpaRepository;
import by.emel.anton.model.dao.implementation.springdatadao.intefaces.UserJpaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;


@ExtendWith(SpringExtension.class)
@SpringBootTest
//@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
//@TestPropertySource("/application.properties")

//@Sql(value = {"classpath:create-user-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)

public class DoctorControllerTest {

    private DoctorController doctorController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private UserJpaRepository userJpaRepository;
    private DoctorJpaRepository doctorJpaRepository;
    private PatientJpaRepository patientJpaRepository;
    private TherapyJpaRepository therapyJpaRepository;

    @Autowired
    public DoctorControllerTest(
            MockMvc mockMvc,
            DoctorController doctorController,
            ObjectMapper objectMapper,
            UserJpaRepository userJpaRepository,
            TherapyJpaRepository therapyJpaRepository,
            DoctorJpaRepository doctorJpaRepository,
            PatientJpaRepository patientJpaRepository) {
        this.mockMvc = mockMvc;
        this.doctorController = doctorController;
        this.objectMapper = objectMapper;
        this.userJpaRepository = userJpaRepository;
        this.therapyJpaRepository = therapyJpaRepository;
        this.doctorJpaRepository = doctorJpaRepository;
        this.patientJpaRepository = patientJpaRepository;
    }

    @BeforeEach
    private void fillInTestDataBase() {
        Doctor doctor = new Doctor();
        doctor.setLogin("logdoc");
        doctor.setPassword("passdoc");
        doctor.setName("namedoc");
        doctor.setBirthday(LocalDate.of(1901, 1, 1));
        doctor.setPatients(new ArrayList<>());

        Patient patient = new Patient();
        patient.setLogin("patlog");
        patient.setPassword("patpass");
        patient.setName("patname");
        patient.setBirthday(LocalDate.of(1990, 1, 1));
        patient.setTherapies(new ArrayList<>());
        userJpaRepository.save(doctor);
        userJpaRepository.save(patient);
        doctor.addPatient(patient);
        patient.setDoctor(doctor);
        userJpaRepository.save(doctor);
        userJpaRepository.save(patient);


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
        requestDoctorDTO.setLogin("logdoc");
        requestDoctorDTO.setPassword("passdoc");

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
     /*   Doctor d = new Doctor();
        UserService userService;
       d =  userService.saveUser(d);*/
    }

    @Test
    public void getDoctorByLoginPass() throws Exception {
        ResponseDoctorDTO responseDoctorDTO = new ResponseDoctorDTO();
        responseDoctorDTO.setId(1);
        responseDoctorDTO.setPatientIds(Arrays.asList(2));
        responseDoctorDTO.setName("namedoc");
        responseDoctorDTO.setLogin("logdoc");

        RequestDoctorDTO requestDoctorDTO = new RequestDoctorDTO();
        requestDoctorDTO.setLogin("logdoc");
        requestDoctorDTO.setPassword("passdoc");
        mockMvc.perform(MockMvcRequestBuilders.post("/doctor/login")
                .content(objectMapper.writeValueAsString(requestDoctorDTO)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(responseDoctorDTO)));
    }

    @Test
    @Transactional
    public void setPatientToDoctor() throws Exception {
        Patient patient = new Patient();
        patient.setTherapies(new ArrayList<>());
        patient.setBirthday(LocalDate.of(2000, 1, 1));
        patient.setPassword("111");
        patient.setLogin("loginPatient");
        patient.setName("namePatient");
        userJpaRepository.save(patient);

        String newIdPatientParam =
                String.valueOf(patientJpaRepository.getPatientIdByLoginAndPassword("loginPatient", "111").orElse(0));

        mockMvc.perform(MockMvcRequestBuilders.post("/doctor/patient/set")
                .sessionAttr("doctorId", "1")
                .param("patientId", newIdPatientParam))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void setTherapyToPatient() {
    }

    @Test
    public void logout() {
    }
}