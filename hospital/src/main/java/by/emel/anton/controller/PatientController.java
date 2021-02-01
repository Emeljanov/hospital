package by.emel.anton.controller;

import by.emel.anton.facade.patient.PatientFacade;
import by.emel.anton.facade.patient.RequestPatientDTO;
import by.emel.anton.facade.patient.ResponsePatientDTO;
import by.emel.anton.facade.therapy.ResponseTherapyDTO;
import by.emel.anton.facade.therapy.TherapyFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    PatientFacade patientFacade;
    @Autowired
    TherapyFacade therapyFacade;

    @GetMapping("/by_id")
    public ResponsePatientDTO getPatientById(@RequestParam int patientId) {
        LOGGER.info("Get patient by id: {}", patientId);

        return patientFacade.getPatientById(patientId);
    }

    @PostMapping("/login")
    public ResponsePatientDTO getPatientByLoginPass(@RequestBody @Validated RequestPatientDTO requestPatientDTO) {

        String login = requestPatientDTO.getLogin();
        String password = requestPatientDTO.getPassword();
        LOGGER.info("Get patient by login :{}, password : {}", login, password);

        return patientFacade.getPatientByLogPass(login, password);
    }

    @GetMapping("/get_therapy")
    public ResponseTherapyDTO getTherapyById(@RequestParam int therapyId) {
        LOGGER.info("Get therapy by id : {}", therapyId);

        return therapyFacade.getTherapy(therapyId);
    }
}
