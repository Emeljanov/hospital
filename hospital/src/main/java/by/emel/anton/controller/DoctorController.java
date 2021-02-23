package by.emel.anton.controller;

import by.emel.anton.facade.doctor.DoctorFacade;
import by.emel.anton.facade.therapy.RequestTherapyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/doctor")
public class DoctorController {

    private DoctorFacade doctorFacade;

    @Autowired
    public DoctorController(DoctorFacade doctorFacade) {
        this.doctorFacade = doctorFacade;
    }

    private static final Logger logger = LoggerFactory.getLogger(DoctorController.class);

    @PostMapping("/patient/set")
    @PreAuthorize("hasAuthority('develop:write')")
    public ResponseEntity<Object> setPatientToDoctor(@RequestParam int patientId) {

        logger.info("Set patient id: {} ", patientId);
        doctorFacade.setPatientToDoctor(patientId);
        return ResponseEntity.ok().build();

    }

    @PostMapping("/patient/therapy/set")
    @PreAuthorize("hasAuthority('develop:write')")
    public ResponseEntity<Object> setTherapyToPatient(@RequestBody RequestTherapyDTO requestTherapyDTO) {

        logger.info("Set therapy description : {} to patient id: {}",
                requestTherapyDTO.getDescription(), requestTherapyDTO.getPatientId());
        doctorFacade.setTherapyToPatient(requestTherapyDTO);
        return ResponseEntity.ok().build();
    }
}


