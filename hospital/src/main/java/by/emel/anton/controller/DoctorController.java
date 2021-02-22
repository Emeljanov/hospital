package by.emel.anton.controller;

import by.emel.anton.facade.doctor.DoctorFacade;
import by.emel.anton.facade.doctor.RequestDoctorDTO;
import by.emel.anton.facade.doctor.ResponseDoctorDTO;
import by.emel.anton.facade.therapy.RequestTherapyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    private DoctorFacade doctorFacade;
    private HttpSession httpSession;

    @Autowired
    public DoctorController(DoctorFacade doctorFacade, HttpSession httpSession) {
        this.doctorFacade = doctorFacade;
        this.httpSession = httpSession;
    }

    private static final Logger logger = LoggerFactory.getLogger(DoctorController.class);
/*
    @PostMapping("/login")
    public ResponseDoctorDTO getDoctorByLoginPass(@RequestBody @Valid RequestDoctorDTO requestDoctorDTO) {

        String login = requestDoctorDTO.getLogin();
        String password = requestDoctorDTO.getPassword();
        logger.info("Get doctor by login: {}, password: {}", login, password);

        return doctorFacade.getDoctorByLoginPassword(login, password);
    }*/

    @PostMapping("/patient/set")
    @PreAuthorize("hasAuthority('develop:write')")
    public ResponseEntity<Object> setPatientToDoctor(@RequestParam int patientId) {

        logger.info("Set patient id: {} ", patientId);
        doctorFacade.setPatientToDoctor(patientId);
        return ResponseEntity.ok().build();

    }

    @PostMapping("/patient/therapy/set")
    @PreAuthorize("hasAuthority('develop:write')")
    public ResponseEntity<Object> setTherapyToPatient(@RequestBody RequestTherapyDTO requestTherapyDTO, @SessionAttribute("doctorId") int doctorId) {

        logger.info("Set therapy description : {} to patient id: {} by doctor id : {}",
                requestTherapyDTO.getDescription(), requestTherapyDTO.getPatientId(), doctorId);
        doctorFacade.setTherapyToPatient(doctorId, requestTherapyDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    @PreAuthorize("hasAuthority('develop:read')")
    public ResponseEntity<Object> logout(@SessionAttribute("doctorId") int doctorId) {
        httpSession.invalidate();
        logger.info("Session invalidated, id : {}", doctorId);

        return ResponseEntity.ok().build();
    }
}


