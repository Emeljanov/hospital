package by.emel.anton.controller;

import by.emel.anton.model.entity.therapy.Therapy;
import by.emel.anton.model.entity.users.patients.Patient;
import by.emel.anton.model.dao.implementation.springdatadao.intefaces.PatientJpaRepository;
import by.emel.anton.model.dao.implementation.springdatadao.intefaces.TherapyJpaRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
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
    private static final String ATR_DOC_ID = "doctorId";
    private static final String PARAM_PAT_ID = "patientId";

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

    @Test
    public void getDoctorByLoginPass() throws Exception {

        String filePathRequestBody = "src/test/resources/requestBodyGetDoctorByLP.json";
        String filePathResponseBody = "src/test/resources/responseBodyGetDoctorByLP.json";

        mockMvc.perform(MockMvcRequestBuilders.post("/doctor/login")
                .content(getJsonStringFromFile(filePathRequestBody)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(getJsonStringFromFile(filePathResponseBody)));
    }

    @Test
    public void setPatientToDoctor() throws Exception {

        String paramNewPatientId = "3";
        int doctorId = 1;

        mockMvc.perform(MockMvcRequestBuilders.post("/doctor/patient/set")
                .sessionAttr(ATR_DOC_ID, doctorId)
                .param(PARAM_PAT_ID, paramNewPatientId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Patient patientFromDb = patientJpaRepository.findById(Integer.valueOf(paramNewPatientId)).orElseThrow(Exception::new);
        int docIdFromDb = patientFromDb.getDoctor().getId();
        assertEquals(docIdFromDb, doctorId);
    }

    @Test
    public void setTherapyToPatient() throws Exception {
        String requestThBodyPath = "src/test/resources/requestTherapyBodySetThToPat.json";
        int doctorId = 1;

        JSONObject jsonTherapy = new JSONObject(getJsonStringFromFile(requestThBodyPath));

        String description = jsonTherapy.getString("description");
        LocalDate stDate = LocalDate.parse(jsonTherapy.getString("startDate"));
        LocalDate endDate = LocalDate.parse(jsonTherapy.getString("endDate"));
        int patientId = jsonTherapy.getInt("patientId");

        mockMvc.perform(MockMvcRequestBuilders.post("/doctor/patient/therapy/set")
                .content(getJsonStringFromFile(requestThBodyPath)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .sessionAttr(ATR_DOC_ID, doctorId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Therapy therapy = therapyJpaRepository.findAll().stream().findFirst().orElseThrow(Exception::new);
        assertEquals(therapy.getDescription(), description);
        assertEquals(therapy.getPatient().getId(), patientId);
        assertEquals(therapy.getStartDate(), stDate);
        assertEquals(therapy.getEndDate(), endDate);
    }

    @Test
    public void logout() throws Exception {
        int doctorId = 1;
        mockMvc.perform(MockMvcRequestBuilders
                .post("/doctor/logout").sessionAttr(ATR_DOC_ID, doctorId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private String getJsonStringFromFile(String path) throws IOException {
        return Files.readString(Paths.get(path));
    }
}