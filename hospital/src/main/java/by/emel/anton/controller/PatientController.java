package by.emel.anton.controller;

import by.emel.anton.facade.patient.PatientFacade;
import by.emel.anton.facade.patient.ResponsePatientDTO;
import by.emel.anton.facade.therapy.ResponseTherapyDTO;
import by.emel.anton.facade.therapy.TherapyFacade;
import by.emel.anton.service.exception.UserServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/patient")
public class PatientController {

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);
    private PatientFacade patientFacade;
    private TherapyFacade therapyFacade;

    @Autowired
    public PatientController(PatientFacade patientFacade, TherapyFacade therapyFacade) {
        this.patientFacade = patientFacade;
        this.therapyFacade = therapyFacade;
    }

    @GetMapping("/by-id")
    @PreAuthorize("hasAuthority('develop:read')")
    public ResponsePatientDTO getPatientById(@RequestParam int patientId) throws UserServiceException {
        logger.info("Get patient by id: {}", patientId);

        return patientFacade.getPatientById(patientId);
    }

    @GetMapping("/therapy/get/{id}")
    @PreAuthorize("hasAuthority('develop:read')")
    public ResponseTherapyDTO getTherapyById(@PathVariable(name = "id") int therapyId) {
        logger.info("Get therapy by id : {}", therapyId);

        return therapyFacade.getTherapy(therapyId);
    }
}
