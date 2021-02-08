package by.emel.anton.controller;

import by.emel.anton.facade.doctor.RequestDoctorDTO;
import by.emel.anton.facade.doctor.ResponseDoctorDTO;
import by.emel.anton.facade.therapy.RequestTherapyDTO;
import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.beans.users.User;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DoctorControllerTest {
    private static final String ATR_DOC_ID = "doctorId";
    private static final String PARAM_PAT_ID = "patientId";
    private static final String DEFAULT_DOCTOR_NAME = "defaultDoctorName";
    private static final String DEFAULT_DOCTOR_LOGIN = "defaultDoctorLogin";
    private static final String DEFAULT_DOCTOR_PASS = "defaultPass";
    private static final String DEFAULT_PATIENT_NAME = "defaultPatientName";
    private static final String DEFAULT_PATIENT_LOGIN = "defaultPatientLogin";
    private static final String DEFAULT_PATIENT_PASS = "defaultPatientPass";

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private UserJpaRepository userJpaRepository;
    private DoctorJpaRepository doctorJpaRepository;
    private PatientJpaRepository patientJpaRepository;
    private TherapyJpaRepository therapyJpaRepository;

    @Autowired
    public DoctorControllerTest(
            MockMvc mockMvc,
            ObjectMapper objectMapper,
            UserJpaRepository userJpaRepository,
            TherapyJpaRepository therapyJpaRepository,
            DoctorJpaRepository doctorJpaRepository,
            PatientJpaRepository patientJpaRepository) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.userJpaRepository = userJpaRepository;
        this.therapyJpaRepository = therapyJpaRepository;
        this.doctorJpaRepository = doctorJpaRepository;
        this.patientJpaRepository = patientJpaRepository;
    }

    @BeforeEach
    private void fillInTestDataBase() {
        userJpaRepository.deleteAll();
        therapyJpaRepository.deleteAll();

        Doctor doctor = new Doctor();
        doctor.setLogin(DEFAULT_DOCTOR_LOGIN);
        doctor.setPassword(DEFAULT_DOCTOR_PASS);
        doctor.setName(DEFAULT_DOCTOR_NAME);
        doctor.setBirthday(LocalDate.of(1901, 1, 1));
        doctor.setPatients(new ArrayList<>());

        Patient patient = new Patient();
        patient.setLogin(DEFAULT_PATIENT_LOGIN);
        patient.setPassword(DEFAULT_PATIENT_PASS);
        patient.setName(DEFAULT_PATIENT_NAME);
        patient.setBirthday(LocalDate.of(1990, 1, 1));
        patient.setTherapies(new ArrayList<>());
        doctor.addPatient(patient);
        patient.setDoctor(doctor);
        userJpaRepository.save(doctor);
        userJpaRepository.save(patient);
    }

    @Test
    public void getDoctorByLoginPass() throws Exception {
        Doctor doctor = doctorJpaRepository.findAll().stream().findFirst().orElseThrow(Exception::new);
        ResponseDoctorDTO responseDoctorDTO = new ResponseDoctorDTO();
        responseDoctorDTO.setId(doctor.getId());
        responseDoctorDTO.setPatientIds(doctor.getPatients().stream().map(User::getId).collect(Collectors.toList()));
        responseDoctorDTO.setName(doctor.getName());
        responseDoctorDTO.setLogin(doctor.getLogin());
        String jsonResponseBody = objectMapper.writeValueAsString(responseDoctorDTO);

        RequestDoctorDTO requestDoctorDTO = new RequestDoctorDTO();
        requestDoctorDTO.setLogin(doctor.getLogin());
        requestDoctorDTO.setPassword(doctor.getPassword());
        String jsonRequestBody = objectMapper.writeValueAsString(requestDoctorDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/doctor/login")
                .content(jsonRequestBody).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jsonResponseBody));
    }

    @Test
    public void setPatientToDoctor() throws Exception {
        String patientName = "SetPatientName";
        String patientPass = "SetPatientPass";
        String patientLogin = "SetPatientLogin";
        LocalDate patientBirthday = LocalDate.of(2000, 1, 1);

        Patient patient = new Patient();
        patient.setTherapies(new ArrayList<>());
        patient.setBirthday(patientBirthday);
        patient.setPassword(patientPass);
        patient.setLogin(patientLogin);
        patient.setName(patientName);

        userJpaRepository.save(patient);

        String newPatientId =
                String.valueOf(patientJpaRepository.getPatientIdByLoginAndPassword(patientLogin, patientPass).orElse(0));
        int doctorId =
                doctorJpaRepository.getDoctorIdByLoginAndPassword(DEFAULT_DOCTOR_LOGIN, DEFAULT_DOCTOR_PASS).orElse(0);

        mockMvc.perform(MockMvcRequestBuilders.post("/doctor/patient/set")
                .sessionAttr(ATR_DOC_ID, doctorId)
                .param(PARAM_PAT_ID, newPatientId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Patient patientFromDb = patientJpaRepository.findById(Integer.valueOf(newPatientId)).orElseThrow(Exception::new);
        int docIdFromDb = patientFromDb.getDoctor().getId();
        assertEquals(docIdFromDb, doctorId);
    }

    @Test
    public void setTherapyToPatient() throws Exception {
        if (!therapyJpaRepository.findAll().isEmpty()) throw new Exception();

        String description = "description";
        int doctorID = doctorJpaRepository.getDoctorIdByLoginAndPassword(DEFAULT_DOCTOR_LOGIN, DEFAULT_DOCTOR_PASS).orElse(0);
        int patientId = patientJpaRepository.getPatientIdByLoginAndPassword(DEFAULT_PATIENT_LOGIN, DEFAULT_PATIENT_PASS).orElse(0);
        LocalDate stDate = LocalDate.of(2021, 2, 2);
        LocalDate endDate = LocalDate.of(2030, 1, 1);

        RequestTherapyDTO requestTherapyDTO = new RequestTherapyDTO();
        requestTherapyDTO.setPatientId(patientId);
        requestTherapyDTO.setStartDate(stDate);
        requestTherapyDTO.setEndDate(endDate);
        requestTherapyDTO.setDescription(description);
        String jsonRequestTherapyBody = objectMapper.writeValueAsString(requestTherapyDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/doctor/patient/therapy/set")
                .content(jsonRequestTherapyBody)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .sessionAttr(ATR_DOC_ID, doctorID))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Therapy therapy = therapyJpaRepository.findAll().stream().findFirst().orElseThrow(Exception::new);
        assertEquals(therapy.getDescription(), description);
        assertEquals(therapy.getPatient().getId(), patientId);
        assertEquals(therapy.getStartDate(), stDate);
        assertEquals(therapy.getEndDate(), endDate);
    }

    @Test
    public void logout() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/doctor/logout").sessionAttr(ATR_DOC_ID, 1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}