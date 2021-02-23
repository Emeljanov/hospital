package by.emel.anton.controller;

import by.emel.anton.model.dao.implementation.springdatadao.intefaces.PatientJpaRepository;
import by.emel.anton.model.dao.implementation.springdatadao.intefaces.TherapyJpaRepository;
import by.emel.anton.model.entity.therapy.Therapy;
import by.emel.anton.model.entity.users.patients.Patient;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(value = {"classpath:before-each.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class DoctorControllerTest {

    private static final String KEY_PAT_ID = "patientId";
    private static final String KEY_LOGIN = "login";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_AUTHORIZATION = "Authorization";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_START_DATE = "startDate";
    private static final String KEY_END_DATE = "endDate";
    private static final String REQUEST_BODY_THERAPY_PATH = "src/test/resources/requestTherapyBodySetThToPat.json";
    private static final String REQUEST_BODY_DOCTOR_PATH = "src/test/resources/requestBodyGetDoctorByLP.json";

    private MockMvc mockMvc;
    private PatientJpaRepository patientJpaRepository;
    private TherapyJpaRepository therapyJpaRepository;

    @Autowired
    public DoctorControllerTest(
            MockMvc mockMvc,
            TherapyJpaRepository therapyJpaRepository,
            PatientJpaRepository patientJpaRepository) {
        this.mockMvc = mockMvc;
        this.therapyJpaRepository = therapyJpaRepository;
        this.patientJpaRepository = patientJpaRepository;

    }

    @AfterEach
    public void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void setPatientToDoctor() throws Exception {

        String paramNewPatientId = "3";

        JSONObject jsonDoctor = new JSONObject(getJsonStringFromFile(REQUEST_BODY_DOCTOR_PATH));
        String doctorLogin = jsonDoctor.getString(KEY_LOGIN);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login/doctor")
                .content(getJsonStringFromFile(REQUEST_BODY_DOCTOR_PATH)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String responseAsString = mvcResult.getResponse().getContentAsString();
        JSONObject responseJsonObject = new JSONObject(responseAsString);
        String token = responseJsonObject.getString(KEY_TOKEN);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/doctor/patient/set")
                .header(KEY_AUTHORIZATION, token)
                .param(KEY_PAT_ID, paramNewPatientId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Patient patientFromDb = patientJpaRepository.findById(Integer.valueOf(paramNewPatientId)).orElseThrow(Exception::new);
        String docLoginFromDb = patientFromDb.getDoctor().getLogin();
        assertEquals(docLoginFromDb, doctorLogin);
    }

    @Test
    public void setTherapyToPatient() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login/doctor")
                .content(getJsonStringFromFile(REQUEST_BODY_DOCTOR_PATH)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String responseAsString = mvcResult.getResponse().getContentAsString();
        JSONObject responseJsonObject = new JSONObject(responseAsString);
        String token = responseJsonObject.getString(KEY_TOKEN);

        JSONObject jsonTherapy = new JSONObject(getJsonStringFromFile(REQUEST_BODY_THERAPY_PATH));

        String description = jsonTherapy.getString(KEY_DESCRIPTION);
        LocalDate stDate = LocalDate.parse(jsonTherapy.getString(KEY_START_DATE));
        LocalDate endDate = LocalDate.parse(jsonTherapy.getString(KEY_END_DATE));
        int patientId = jsonTherapy.getInt(KEY_PAT_ID);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/doctor/patient/therapy/set")
                .header(KEY_AUTHORIZATION, token)
                .content(getJsonStringFromFile(REQUEST_BODY_THERAPY_PATH)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Therapy therapy = therapyJpaRepository.findAll().stream().findFirst().orElseThrow(Exception::new);
        assertEquals(therapy.getDescription(), description);
        assertEquals(therapy.getPatient().getId(), patientId);
        assertEquals(therapy.getStartDate(), stDate);
        assertEquals(therapy.getEndDate(), endDate);
    }

    private String getJsonStringFromFile(String path) throws IOException {
        return Files.readString(Paths.get(path));
    }
}