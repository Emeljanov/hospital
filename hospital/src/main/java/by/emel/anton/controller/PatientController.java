package by.emel.anton.controller;

import by.emel.anton.facade.patient.PatientFacade;
import by.emel.anton.facade.patient.ResponsePatientDTO;
import by.emel.anton.facade.therapy.ResponseTherapyDTO;
import by.emel.anton.facade.therapy.TherapyFacade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/patient")
public class PatientController {

    private PatientFacade patientFacade;
    private TherapyFacade therapyFacade;

    @GetMapping("/by-id")
    @PreAuthorize("hasAuthority('develop:read')")
    public ResponsePatientDTO getPatientById(@RequestParam int patientId) {
        log.info("Get patient by id: {}", patientId);

        return patientFacade.getPatientById(patientId);
    }

    @GetMapping("/therapy/get/{id}")
    @PreAuthorize("hasAuthority('develop:read')")
    public ResponseTherapyDTO getTherapyById(@PathVariable(name = "id") int therapyId) {
        log.info("Get therapy by id : {}", therapyId);

        return therapyFacade.getTherapy(therapyId);
    }
}
