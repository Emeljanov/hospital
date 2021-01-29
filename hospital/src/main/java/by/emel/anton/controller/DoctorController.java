package by.emel.anton.controller;

import by.emel.anton.facade.doctor.DoctorFacade;
import by.emel.anton.facade.doctor.RequestDoctorDTO;
import by.emel.anton.facade.doctor.ResponseDoctorDTO;
import by.emel.anton.facade.therapy.RequestTherapyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    DoctorFacade doctorFacade;

    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorController.class);

    @PostMapping("/login")
    public ResponseDoctorDTO getDoctorByLoginPass(@RequestBody @Validated RequestDoctorDTO requestDoctorDTO) {

        String login = requestDoctorDTO.getLogin();
        String password = requestDoctorDTO.getPassword();
        LOGGER.info("Get doctor by login: {}, password: {}", login, password);


        return doctorFacade.getDoctorByLoginPassword(login, password);
    }

    @PostMapping("/login/setpatient")
    public void setPatientToDoctor(@SessionAttribute("doctorId") int doctorId, @RequestParam int patientId) {

        LOGGER.info("Set patient id: {} by doctor id: {}", patientId, doctorId);
        doctorFacade.setPatientToDoctor(doctorId, patientId);

    }

    @PostMapping("/login/settherapy")
    public void setTherapyToPatient(@RequestBody RequestTherapyDTO requestTherapyDTO, @SessionAttribute("doctorId") int doctorId) {

        LOGGER.info("Set therapy description : {} to patient id: {} by doctor id : {}",
                requestTherapyDTO.getDescription(), requestTherapyDTO.getPatientId(), doctorId);
        doctorFacade.setTherapyToPatient(doctorId, requestTherapyDTO);
    }
}


