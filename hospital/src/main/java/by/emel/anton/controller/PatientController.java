package by.emel.anton.controller;

import by.emel.anton.facade.patient.PatientFacade;
import by.emel.anton.facade.patient.RequestPatientDTO;
import by.emel.anton.facade.patient.ResponsePatientDTO;
import by.emel.anton.facade.therapy.ResponseTherapyDTO;
import by.emel.anton.facade.therapy.TherapyFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);
    private PatientFacade patientFacade;
    private TherapyFacade therapyFacade;
    private HttpSession httpSession;

    @Autowired
    public PatientController(PatientFacade patientFacade, TherapyFacade therapyFacade, HttpSession httpSession) {
        this.patientFacade = patientFacade;
        this.therapyFacade = therapyFacade;
        this.httpSession = httpSession;
    }

    @GetMapping("/by-id")
    @PreAuthorize("hasAuthority('develop:read')")
    public ResponsePatientDTO getPatientById(@RequestParam int patientId) {
        logger.info("Get patient by id: {}", patientId);

        return patientFacade.getPatientById(patientId);
    }

    @PostMapping("/login")
    public ResponsePatientDTO getPatientByLoginPass(@RequestBody @Valid RequestPatientDTO requestPatientDTO) {

        String login = requestPatientDTO.getLogin();
        String password = requestPatientDTO.getPassword();
        logger.info("Get patient by login :{}, password : {}", login, password);

        return patientFacade.getPatientByLogPass(login, password);
    }

    @GetMapping("/therapy/get/{id}")
    @PreAuthorize("hasAuthority('develop:read')")
    public ResponseTherapyDTO getTherapyById(@PathVariable(name = "id") int therapyId) {
        logger.info("Get therapy by id : {}", therapyId);

        return therapyFacade.getTherapy(therapyId);
    }

    @PostMapping("/logout")
    @PreAuthorize("hasAuthority('develop:read')")
    public ResponseEntity<Object> logout(@SessionAttribute("patientId") int patientId) {
        httpSession.invalidate();
        logger.info("Session invalidated, id : {}", patientId);

        return ResponseEntity.ok().build();
    }
}
